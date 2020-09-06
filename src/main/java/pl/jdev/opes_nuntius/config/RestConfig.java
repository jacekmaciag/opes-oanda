package pl.jdev.opes_nuntius.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import pl.jdev.opes_commons.rest.client.IntegrationClient;
import pl.jdev.opes_commons.rest.interceptor.AuthInterceptor;
import pl.jdev.opes_commons.rest.interceptor.RestLoggingInterceptor;
import pl.jdev.opes_nuntius.domain.ConfigurableClient;
import pl.jdev.opes_nuntius.domain.client_config_model.ClientConfigImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
@Slf4j
public class RestConfig {


    @Bean
    @DependsOn({"requestFactory"})
    @Autowired
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
                              List<ClientHttpRequestInterceptor> restInterceptors,
                              MappingJackson2HttpMessageConverter messageConverter) {
        RestTemplate restTemplate = restTemplateBuilder
                .additionalInterceptors(restInterceptors)
                .additionalMessageConverters(messageConverter)
                .build();
        restTemplate.setRequestFactory(requestFactory());
        return restTemplate;
    }

    @Bean
    ClientHttpRequestFactory requestFactory() {
        return new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
    }

    @Bean
    RestLoggingInterceptor restLoggingInterceptor() {
        return new RestLoggingInterceptor();
    }

//    @Bean
//    UserAgentInterceptor userAgentInterceptor() {
//        return new UserAgentInterceptor();
//    }

    @Bean
    AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Bean
    @DependsOn({"auth"})
    @Autowired
    Map.Entry authHeader(String auth) {
        return Map.entry(AUTHORIZATION, auth);
    }

    @Bean
    Map.Entry contentTypeHeader() {
        return Map.entry(CONTENT_TYPE, APPLICATION_JSON.toString());
    }

    @Bean
    @DependsOn({"restTemplate"})
    @Autowired
    IntegrationClient integrationClient(RestTemplate restTemplate,
                                        @Value("${opes.integration.host}") String integrationHostUrl,
                                        @Value("${opes.integration.port}") String integrationPort,
                                        @Value("${opes.integration.version}") String integrationVersion) {
        return new IntegrationClient(restTemplate, String.format("%s:%s/%s", integrationHostUrl, integrationPort, integrationVersion));
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        //TODO: will probably need to be changed after mappings are working.
        mapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return mapper;
    }

    private Predicate<File> isYamlFile = (file) -> {
        String path = file.getPath();
        boolean result = path.matches(".+\\.yaml");
        if (!result) {
            log.warn("Rejecting file '%s' - is not yaml file", path);
        }
        return result;
    };

    private Function<File, FileInputStream> streamFile = (file) -> {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.error("Failed processing file", e);
        }
        return null;
    };

    private Function<InputStream, ClientConfigImpl> streamToConfigObject = (io) -> new Yaml(new Constructor(ClientConfigImpl.class)).load(io);

    @Bean
    List<ClientConfigImpl> yamlClientsConfig(@Value("${nuntius.client_configuration.location}") String configLocation) {
        log.info("Reading client configuration files from '%s' location", configLocation);
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL configPath = classLoader.getResource(configLocation);
        File[] configFiles = new File(configPath.getPath()).listFiles();
        log.info("Found %d client configuration files.", configFiles.length);
        return Arrays.stream(configFiles)
                .peek(file -> log.info("Processing file '%s'", file.getPath()))
                .filter(isYamlFile)
                .map(streamFile)
                .map(streamToConfigObject)
                .peek(config -> log.info("Successfully created config for client '%s'", config.getName()))
                .collect(Collectors.toList());
    }

    BiFunction<ClientConfigImpl, RestTemplate, ConfigurableClient> clientFromConfig = (config, restTemplate) -> new ConfigurableClient(restTemplate, config);

    @Bean
    List<ConfigurableClient> clients(List<ClientConfigImpl> yamlClientsConfig, RestTemplate restTemplate) {
        log.info("Creating %d clients", yamlClientsConfig.size());
        return yamlClientsConfig.stream()
                .peek(config -> log.info("Creating client '%s'", config.getName()))
                .map(config -> clientFromConfig.apply(config, restTemplate))
                .peek(client -> log.info("Successfully created client '%s'", client.getName()))
                .collect(Collectors.toList());
    }
}
