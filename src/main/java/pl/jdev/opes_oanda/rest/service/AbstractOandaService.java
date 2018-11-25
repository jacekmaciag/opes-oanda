package pl.jdev.opes_oanda.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.domain.AbstractEntity;
import pl.jdev.opes_commons.rest.HttpService;
import pl.jdev.opes_oanda.config.Url;

@Component
public abstract class AbstractOandaService<T extends AbstractEntity> extends HttpService {
    protected Url url;

    @Autowired
    public AbstractOandaService(RestTemplate restTemplate,
                                Url url) {
        super(restTemplate);
        this.url = url;
    }
}
