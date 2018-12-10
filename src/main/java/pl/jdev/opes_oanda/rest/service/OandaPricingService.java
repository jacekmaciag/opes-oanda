package pl.jdev.opes_oanda.rest.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.domain.pricing.Price;
import pl.jdev.opes_commons.rest.message.response.JsonPricingListWrapper;
import pl.jdev.opes_oanda.config.Url;

import java.util.Collection;

import static java.lang.String.join;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@Log4j2(topic = "CORE - Pricing")
public class OandaPricingService extends AbstractOandaService<Price> {
    @Autowired
    ApplicationContext ctx;

    @Autowired
    public OandaPricingService(RestTemplate restTemplate, Url url) {
        super(restTemplate, url);
    }

    public Collection<Price> getPrices(String accountId,
                                       Collection<String> instruments,
                                       String since,
                                       boolean includeUnitsAvailable,
                                       boolean includeHomeConversions) {
        Collection<Price> priceList = this.restTemplate
                .exchange(fromPath(url.PRICING_URL)
                                .queryParam("instruments", join(",", instruments))
                                .queryParam("since", since)
                                .queryParam("includeUnitsAvailable", includeUnitsAvailable)
                                .queryParam("includeHomeConversions", includeHomeConversions)
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        JsonPricingListWrapper.class)
                .getBody()
                .getPrices();
        return priceList;
    }

//    //    @Scheduled(cron = "#{${pricing.interval} ?: '0 0 0/1 * * ?'}")
//    private void fetchPricesAtInterval() {
//        String accountId = (String) ctx.getBean("accountId");
//        Collection<String> currencyPairs = (Collection<String>) ctx.getBean("currencyPairs");
//        log.info(format("Fetching prices for currency pairs %s...", join(", ", currencyPairs)));
//        Collection<Price> prices = this.restTemplate
//                .exchange(fromPath(url.PRICING_URL)
//                                .queryParam("instruments", join(",", currencyPairs))
//                                .buildAndExpand(accountId)
//                                .toString(),
//                        GET,
//                        new HttpEntity<>(EMPTY, null),
//                        JsonPricingListWrapper.class)
//                .getBody()
//                .getPrices();
//        log.info(format("Received prices %s", prices));
//        Message<Collection<Price>> priceMsg = withPayload(prices).build();
////        ctx.getBean("pricingEventGateway", PricingEventGateway.class).send(priceMsg);
//    }
}
