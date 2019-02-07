package pl.jdev.opes_oanda.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.domain.position.Position;
import pl.jdev.opes_oanda.config.Url;

public class OandaPositionService extends AbstractOandaService {
    @Autowired
    public OandaPositionService(RestTemplate restTemplate,
                                Url url) {
        super(restTemplate, url);
    }

    public Position[] getAll() {
        return new Position[0];
    }

    public Position get(String id) {
        return null;
    }
}
