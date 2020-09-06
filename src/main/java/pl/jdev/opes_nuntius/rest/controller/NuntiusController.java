package pl.jdev.opes_nuntius.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jdev.opes_nuntius.domain.RequestModel;
import pl.jdev.opes_nuntius.rest.RestManager;

@RestController
@RequestMapping("/")
@Slf4j
public class NuntiusController {

    private RestManager restManager;

    public NuntiusController(RestManager restManager) {
        this.restManager = restManager;
    }

    @PostMapping
    public ResponseEntity externalRequest(final @RequestBody RequestModel input) throws Exception {
        log.debug("Received request with body\n{}", input);
        return restManager.executeRequest(input);
    }
}
