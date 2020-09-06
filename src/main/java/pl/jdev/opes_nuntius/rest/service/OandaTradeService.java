package pl.jdev.opes_nuntius.rest.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jdev.opes_commons.domain.ClientExtensions;
import pl.jdev.opes_commons.domain.trade.Trade;
import pl.jdev.opes_nuntius.config.Url;
import pl.jdev.opes_nuntius.domain.trade.OandaTrade;
import pl.jdev.opes_nuntius.mapper.OandaTradeMapper;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static pl.jdev.opes_nuntius.rest.service.OandaResponseHandler.unpack;


@Component
@Log4j2(topic = "CORE - OandaTrade")
public class OandaTradeService extends AbstractOandaService {

    @Autowired
    private OandaTradeMapper mapper;

    public OandaTradeService(RestTemplate restTemplate, Url url) {
        super(restTemplate, url);
    }

    public List<Trade> getAllTrades(String accountId) {
        return ((List<OandaTrade>) unpack(this.restTemplate
                        .exchange(UriComponentsBuilder.newInstance()
                                        .scheme(ReferenceUriSchemesSupported.HTTPS.toString())
                                        .host(url.OANDA_HOST)
                                        .path(url.TRADE_LIST_URL)
                                        .buildAndExpand(accountId)
                                        .toString(),
                                GET,
                                new HttpEntity<>(null, null),
                                String.class)
                        .getBody(),
                OandaTrade.class,
                "trades"))
                .stream()
//                .map(trade -> (OandaTrade) trade)
                .map(mapper::convertToEntity)
                .collect(toList());
    }

    public Trade getTrade(String accountId, String tradeId) {
        return mapper.convertToEntity(
                (OandaTrade) unpack(this.restTemplate
                                .exchange(UriComponentsBuilder.newInstance()
                                                .scheme(ReferenceUriSchemesSupported.HTTPS.toString())
                                                .host(url.OANDA_HOST)
                                                .path(url.SINGLE_TRADE_URL)
                                                .buildAndExpand(accountId, tradeId)
                                                .toString(),
                                        GET,
                                        new HttpEntity<>(EMPTY, null),
                                        String.class)
                                .getBody(),
                        OandaTrade.class,
                        "trade"));
    }

    public HttpStatus closeTrade(String accountId, String tradeId) {
        return this.restTemplate
                .exchange(UriComponentsBuilder.newInstance()
                                .scheme(ReferenceUriSchemesSupported.HTTPS.toString())
                                .host(url.OANDA_HOST)
                                .path(url.CLOSE_TRADE_URL)
                                .buildAndExpand(accountId, tradeId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        String.class)
                .getStatusCode();
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
