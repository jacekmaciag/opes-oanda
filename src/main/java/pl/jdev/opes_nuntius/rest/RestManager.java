package pl.jdev.opes_nuntius.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.jdev.opes_commons.rest.exception.NotFoundException;
import pl.jdev.opes_commons.rest.message.request.RequestType;
import pl.jdev.opes_nuntius.domain.ConfigurableClient;
import pl.jdev.opes_nuntius.domain.RequestModel;

import java.util.List;
import java.util.function.BiPredicate;

import static java.lang.String.format;

@Service
@Slf4j
public class RestManager {

    private List<ConfigurableClient> clients;

    public RestManager(List<ConfigurableClient> clients) {
        this.clients = clients;
    }

    private BiPredicate<ConfigurableClient, String> hasName = (client, expectName) -> {
        String clientName = client.getName();
        boolean result = expectName.equals(clientName);
        if (!result) {
            log.debug("Rejecting '{}' - does not match name '{}'", clientName, expectName);
        }
        return result;
    };

    public ResponseEntity executeRequest(final RequestModel input) throws Exception {
        log.debug("Starting processing request");

        String forEntity = input.getForEntity();
        log.debug("Request for '{}', searching for client", forEntity);
        ConfigurableClient client = clients.stream()
                .peek(c -> log.debug("Checking client '{}'", c))
                .filter(c -> hasName.test(c, forEntity))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(format("No client available for '{}'", forEntity)));

        client.request(input.getType(), input.getBody());
        return null;
    }
}
