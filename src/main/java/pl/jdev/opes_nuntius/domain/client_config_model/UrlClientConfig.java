package pl.jdev.opes_nuntius.domain.client_config_model;

import lombok.Data;

@Data
public class UrlClientConfig {
    private String scheme;
    private String host;
}
