package pl.jdev.opes_nuntius.rest.service;

import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.domain.position.Position;
import pl.jdev.opes_nuntius.config.Url;

public class OandaPositionService extends AbstractOandaService {

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
