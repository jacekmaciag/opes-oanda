package pl.jdev.opes_oanda.rest.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.domain.ClientExtensions;
import pl.jdev.opes_commons.domain.trade.Trade;
import pl.jdev.opes_commons.rest.wrapper.JsonTradeListWrapper;
import pl.jdev.opes_commons.rest.wrapper.JsonTradeWrapper;
import pl.jdev.opes_oanda.config.Url;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;


@Component
@Log4j2(topic = "CORE - Trade")
public class OandaTradeService extends AbstractOandaService<Trade> {

    @Autowired
    public OandaTradeService(RestTemplate restTemplate, Url url) {
        super(restTemplate, url);
    }

    public List<Trade> getAllTrades(String accountId) {
        return this.restTemplate
                .exchange(fromPath(url.TRADE_LIST_URL)
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        JsonTradeListWrapper.class)
                .getBody()
                .getTrades()
                .stream()
                .collect(toList());
    }

    public List<Trade> getOpenTrades(String accountId) {
        return this.restTemplate
                .exchange(fromPath(url.OPEN_TRADE_LIST_URL)
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        JsonTradeListWrapper.class)
                .getBody()
                .getTrades()
                .stream()
                .collect(toList());
    }

    public Trade getTrade(String accountId, String tradeId) {
        return Stream.of(this.restTemplate
                .exchange(fromPath(url.SINGLE_TRADE_URL)
                                .buildAndExpand(accountId, tradeId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        JsonTradeWrapper.class)
                .getBody()
                .getTrade())
                .findFirst()
                .get();
    }

    public Object closeTrade(String accountId, String tradeId) {
        return this.restTemplate
                .exchange(fromPath(url.CLOSE_TRADE_URL)
                                .buildAndExpand(accountId, tradeId)
                                .toString(),
                        PUT,
                        new HttpEntity<>(EMPTY, null),
                        Trade.class)
                .getBody();
    }

    public Object partiallyCloseTrade(String accountId, String tradeId, int units) {
        //TODO: implement
        return null;
    }

    public Object updateTradeClientExtensions(String accountId, String tradeId, ClientExtensions clientExtensions) {
        //TODO: implement
        return null;
    }

    //TODO: implement last endpoint http://developer.oanda.com/rest-live-v20/trade-ep/ if necessary

}
