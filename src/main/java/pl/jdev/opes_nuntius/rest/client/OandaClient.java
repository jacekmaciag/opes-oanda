package pl.jdev.opes_nuntius.rest.client;

import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.rest.RestService;

public class OandaClient extends RestService {
    String oandaClientHost;

    public OandaClient(RestTemplate restTemplate, String oandaClientHost) {
        super(restTemplate, oandaClientHost);
    }
}
