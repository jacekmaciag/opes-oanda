package pl.jdev.opes_nuntius.domain;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.rest.RestService;
import pl.jdev.opes_commons.rest.message.request.RequestType;
import pl.jdev.opes_nuntius.domain.client_config_model.ClientConfigImpl;
import pl.jdev.opes_nuntius.domain.client_config_model.EndpointClientConfig;

import java.util.function.BiPredicate;

@Data
public class ConfigurableClient extends RestService {
    private String name;
    private ClientConfigImpl config;

    public ConfigurableClient(final RestTemplate restTemplate, final ClientConfigImpl config) {
        super(restTemplate, config.getUrl().getHost());
        this.name = config.getName();
        this.config = config;
    }

    private BiPredicate<EndpointClientConfig, RequestType> matchType = (endpointClientConfig, requestType) -> endpointClientConfig.getType() == requestType;

    public ResponseEntity request(final RequestType type, final Message body) throws Exception {
        EndpointClientConfig endpoint = config
                .getEndpoints()
                .stream()
                .filter(ep -> matchType.test(ep, type))
                .findFirst()
                .orElseThrow(() -> new Exception(String.format("Unable to find endpoint of type '%s' in client '%s'", type, name)));
        return send(endpoint.getPath(), body, endpoint.getMethod(), Account.class);
    }
}
