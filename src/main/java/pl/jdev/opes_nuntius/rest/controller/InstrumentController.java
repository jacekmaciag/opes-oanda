package pl.jdev.opes_nuntius.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.instrument.Candlestick;
import pl.jdev.opes_commons.domain.instrument.CandlestickGranularity;
import pl.jdev.opes_commons.domain.instrument.CandlestickPriceType;
import pl.jdev.opes_nuntius.rest.service.OandaInstrumentService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/instruments/{instrument}/")
public class InstrumentController {
    @Autowired
    OandaInstrumentService oandaInstrumentService;

//    @GetMapping(value = "/candles", params = "count")
//    @ResponseBody
//    public List<Candlestick> getCandlesticksWithCount(@PathVariable(name = "instrument") final String instrument,
//                                                      @RequestParam(value = "priceType") final CandlestickPriceType priceType,
//                                                      @RequestParam(value = "granularity") final CandlestickGranularity granularity,
//                                                      @RequestParam(value = "count") final Integer count) {
//        return oandaInstrumentService.getCandlestickList(
//                instrument,
//                priceType,
//                granularity,
//                count
//        );
//    }

    @GetMapping(value = "/candles", params = {"from", "to"})
    @ResponseBody
    public List<Candlestick> getCandlesticksWithPeriod(@PathVariable(name = "instrument") final String instrument,
                                                       @RequestParam(value = "priceType") final CandlestickPriceType priceType,
                                                       @RequestParam(value = "granularity") final CandlestickGranularity granularity,
                                                       @RequestParam(value = "from") final String from,
                                                       @RequestParam(value = "to", required = false) final String to) throws ParseException {
        return oandaInstrumentService.getCandlestickList(
                instrument,
                priceType,
                granularity,
                from,
                to
        );
    }
}
