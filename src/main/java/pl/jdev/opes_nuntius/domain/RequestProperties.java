package pl.jdev.opes_nuntius.domain;

import org.springframework.http.HttpMethod;

import java.util.UUID;

public class RequestProperties {
    private UUID requestID;
    private String type;
    private HttpMethod method;
    private RequestBaseProperties baseProperties;
    private RequestUriProperties uriProperties;
    private RequestHeadersProperties headersProperties;
    private Class responseType;

}
