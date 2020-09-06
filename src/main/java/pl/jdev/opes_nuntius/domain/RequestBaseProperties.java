package pl.jdev.opes_nuntius.domain;

import pl.jdev.opes_commons.domain.broker.BrokerName;

import javax.persistence.Id;
import javax.print.attribute.standard.ReferenceUriSchemesSupported;

public class RequestBaseProperties {
    @Id
    private BrokerName brokerName;
    private ReferenceUriSchemesSupported uriSchemea;
    private String uriHost;
}
