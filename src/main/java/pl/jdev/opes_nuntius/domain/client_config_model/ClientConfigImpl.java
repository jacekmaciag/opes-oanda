package pl.jdev.opes_nuntius.domain.client_config_model;

import lombok.Data;

import java.util.List;

@Data
public class ClientConfigImpl implements ClientConfig{
    private String name;
    private String auth;
    private UrlClientConfig url;
    private List<EndpointClientConfig> endpoints;
}
