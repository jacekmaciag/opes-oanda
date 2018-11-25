package pl.jdev.opes_oanda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.rest.IntegrationClient;
import pl.jdev.opes_commons.rest.interceptor.AuthInterceptor;
import pl.jdev.opes_commons.rest.interceptor.RestLoggingInterceptor;
import pl.jdev.opes_commons.rest.interceptor.UserAgentInterceptor;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class RestConfig {
    @Bean
    @DependsOn({"requestFactory"})
    @Autowired
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
                              List<ClientHttpRequestInterceptor> restInterceptors,
                              MappingJackson2HttpMessageConverter messageConverter) {
        RestTemplate rt = restTemplateBuilder
                .additionalInterceptors(restInterceptors)
                .messageConverters(messageConverter)
                .build();
        rt.setRequestFactory(requestFactory());
        return rt;
    }

    @Bean
    ClientHttpRequestFactory requestFactory() {
        return new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
    }

    @Bean
    RestLoggingInterceptor restLoggingInterceptor() {
        return new RestLoggingInterceptor();
    }

    @Bean
    UserAgentInterceptor userAgentInterceptor() {
        return new UserAgentInterceptor();
    }

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
                                        @Value("${opes.integration.host}") String integrationHostUrl) {
        return new IntegrationClient(restTemplate, integrationHostUrl);
    }
}
