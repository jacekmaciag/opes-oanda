package pl.jdev.opes_oanda.rest.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;
import pl.jdev.opes_commons.rest.message.response.JsonCandlestickListWrapper;
import pl.jdev.opes_oanda.config.Url;

import java.util.Collection;

import static java.util.Objects.requireNonNull;
import static javax.print.attribute.standard.ReferenceUriSchemesSupported.HTTPS;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;

@Service
@Log4j2(topic = "CORE - Instrument")
public class OandaInstrumentService extends AbstractOandaService {

    @Autowired
    public OandaInstrumentService(RestTemplate restTemplate,
                                  Url url) {
        super(restTemplate, url);
    }

    //UriComponentsBuilder.newInstance()
    //                                .scheme(ReferenceUriSchemesSupported.HTTP.toString())
    //                                .host(integrationHost)
    //                                .path("/data")
    //                                .build()
    //                                .toString(

    public Collection<Candlestick> getCandlestickList(final String instrument,
                                                      final CandlestickPriceType priceType,
                                                      final CandlestickGranularity granularity,
                                                      final int count) {
        return this.getCandlestickList(UriComponentsBuilder.newInstance()
                .scheme(HTTPS.toString())
                .host(url.OANDA_HOST)
                .path(url.CANDLES)
                .queryParam("price", priceType)
                .queryParam("granularity", granularity)
                .queryParam("count", count)
                .buildAndExpand(instrument)
                .toString());
    }

    public Collection<Candlestick> getCandlestickList(final String instrument,
                                                      final CandlestickPriceType priceType,
                                                      final CandlestickGranularity granularity,
                                                      final String from,
                                                      final String to) {
        return this.getCandlestickList(UriComponentsBuilder.newInstance()
                .scheme(HTTPS.toString())
                .host(url.OANDA_HOST)
                .path(url.CANDLES)
                .queryParam("price", priceType)
                .queryParam("granularity", granularity)
                .queryParam("from", from)
                .queryParam("to", to)
                .buildAndExpand(instrument)
                .toString());
    }

    private Collection<Candlestick> getCandlestickList(final String uri) {
        return requireNonNull(this.restTemplate
                .exchange(uri,
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        JsonCandlestickListWrapper.class)
                .getBody())
                .getCandles();
    }
}
