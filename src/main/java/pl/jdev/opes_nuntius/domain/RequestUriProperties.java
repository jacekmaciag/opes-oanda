package pl.jdev.opes_nuntius.domain;

import java.util.UUID;

public class RequestUriProperties {
    private UUID requestId;
    private String endpoint;
    private String[] queryParameters;

    private class RequestQueryParameters {
        private String name;
        private boolean isRequired;
    }
}
