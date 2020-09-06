package pl.jdev.opes_nuntius.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.rest.RestService;
import pl.jdev.opes_nuntius.config.Url;

@Component
public abstract class AbstractOandaService extends RestService {

    protected Url url;
    @Autowired
    OandaResponseHandler responseHandler;

    @Autowired
    public AbstractOandaService(final RestTemplate restTemplate, final Url url) {
        super(restTemplate, url.toString());
    }
}
