package pl.jdev.opes_oanda.rest.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;
import pl.jdev.opes_commons.rest.wrapper.JsonCandlestickListWrapper;
import pl.jdev.opes_oanda.config.Url;

import java.util.Collection;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Service
@Log4j2(topic = "CORE - Instrument")
public class OandaInstrumentService extends AbstractOandaService<Candlestick> {

    @Autowired
    public OandaInstrumentService(RestTemplate restTemplate,
                                  Url url) {
        super(restTemplate, url);
    }

    public Collection<Candlestick> getCandlestickList(String instrument,
                                                      CandlestickPriceType priceType,
                                                      CandlestickGranularity granularity,
                                                      int count) {
        return this.getCandlestickList(fromPath(url.CANDLES)
                .queryParam("price", priceType)
                .queryParam("granularity", granularity)
                .queryParam("count", count)
                .buildAndExpand(instrument)
                .toString());
    }

    public Collection<Candlestick> getCandlestickList(String instrument,
                                                      CandlestickPriceType priceType,
                                                      CandlestickGranularity granularity,
                                                      String from,
                                                      String to) {
        return this.getCandlestickList(fromPath(url.CANDLES)
                .queryParam("price", priceType)
                .queryParam("granularity", granularity)
                .queryParam("from", from)
                .queryParam("to", to)
                .buildAndExpand(instrument)
                .toString());
    }

    private Collection<Candlestick> getCandlestickList(String uri) {
        return requireNonNull(this.restTemplate
                .exchange(uri,
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        JsonCandlestickListWrapper.class)
                .getBody())
                .getCandles();
    }
}
