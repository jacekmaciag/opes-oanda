package pl.jdev.opes_nuntius.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.String.join;

@Configuration
public class OandaAuthConfig {
    @Value("${opes.oanda.auth.prefix}")
    private String prefix;
    @Value("${opes.oanda.auth.token}")
    private String token;

    @Bean
    String auth() {
        return join(" ", prefix, token);
    }
}
