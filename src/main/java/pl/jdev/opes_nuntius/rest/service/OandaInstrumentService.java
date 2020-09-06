package pl.jdev.opes_nuntius.rest.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;
import pl.jdev.opes_nuntius.config.Url;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

import static javax.print.attribute.standard.ReferenceUriSchemesSupported.HTTPS;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static pl.jdev.opes_nuntius.rest.service.OandaResponseHandler.unpackCandles;

@Service
@Log4j2(topic = "CORE - Instrument")
public class OandaInstrumentService extends AbstractOandaService {

    @Value("${opes.date_time_format}")
    String dtFormat;

    @Autowired
    public OandaInstrumentService(RestTemplate restTemplate,
                                  Url url) {
        super(restTemplate, url);
    }

    public List<Candlestick> getCandlestickList(final String instrument,
                                                final CandlestickPriceType priceType,
                                                final CandlestickGranularity granularity,
                                                final String from,
                                                final String to) throws ParseException {
        TemporalUnit timeUnit = granularity.getUnit();
        Instant now = Instant.now().truncatedTo(timeUnit);
        SimpleDateFormat df = new SimpleDateFormat(dtFormat);
        Instant periodStart = df.parse(from).toInstant().truncatedTo(timeUnit);
        Instant periodEnd = now;
        if (to != null)
            periodEnd = df.parse(to).toInstant().minus(10, ChronoUnit.MILLIS); // Minus sme time to avoid BadRequest
        if (periodEnd.compareTo(now) >= 1) {
            long count = Duration.between(periodStart, periodEnd).toMillis() / granularity.getMillis();
            return getCandlestickByCount(instrument, priceType, granularity, count);
        } else {
            return getCandlestickByPeriod(instrument, priceType, granularity, periodStart.toString(), periodEnd.toString());
        }
    }

    private List<Candlestick> getCandlestickByCount(final String instrument,
                                                    final CandlestickPriceType priceType,
                                                    final CandlestickGranularity granularity,
                                                    final long count) {
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

    private List<Candlestick> getCandlestickByPeriod(final String instrument,
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

    private List<Candlestick> getCandlestickList(final String uri) {
        return unpackCandles(this.restTemplate
                .exchange(uri,
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        String.class)
                .getBody());
    }
}
