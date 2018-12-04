package pl.jdev.opes_oanda.rest.controller;

import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;
import pl.jdev.opes_commons.domain.instrument.Instrument;
import pl.jdev.opes_commons.rest.wrapper.JsonCandlestickListWrapper;
import pl.jdev.opes_oanda.rest.service.OandaInstrumentService;

@RestController
@RequestMapping("/instruments/{instrument}/")
public class InstrumentController extends AbstractEntityController<Instrument> {
    @GetMapping(value = "/candles", params = "count")
    @ResponseBody
    public JsonCandlestickListWrapper getCandlesticksWithCount(@PathVariable(name = "instrument") final String instrument,
                                                               @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                                               @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                                               @RequestParam(value = "count") final Integer count) {
        return JsonCandlestickListWrapper.payloadOf(
                ((OandaInstrumentService) entityService).getCandlestickList(
                        instrument,
                        priceType,
                        granularity,
                        count
                ));
    }

    @GetMapping(value = "/candles", params = {"from", "to"})
    @ResponseBody
    public JsonCandlestickListWrapper getCandlesticksWithPeriod(@PathVariable(name = "instrument") final String instrument,
                                                                @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                                                @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                                                @RequestParam(value = "from") final String from,
                                                                @RequestParam(value = "to") final String to) {
        return JsonCandlestickListWrapper.payloadOf(
                ((OandaInstrumentService) entityService).getCandlestickList(
                        instrument,
                        priceType,
                        granularity,
                        from,
                        to
                ));
    }
}
