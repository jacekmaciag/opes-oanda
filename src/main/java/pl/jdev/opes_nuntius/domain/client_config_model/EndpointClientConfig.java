package pl.jdev.opes_nuntius.domain.client_config_model;

import lombok.Data;
import org.springframework.http.HttpMethod;
import pl.jdev.opes_commons.rest.message.request.RequestType;

@Data
public class EndpointClientConfig {
    private RequestType type;
    private HttpMethod method;
    private String path;
}
