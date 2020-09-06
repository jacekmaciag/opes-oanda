package pl.jdev.opes_nuntius.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.trade.Trade;
import pl.jdev.opes_nuntius.rest.service.OandaTradeService;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/trades")
public class TradeController {
    @Autowired
    private OandaTradeService oandaTradeService;

    @GetMapping
    @ResponseBody
    public List<Trade> getAllTrades(@PathVariable(name = "accountId") final String accountId) {
        return oandaTradeService.getAllTrades(accountId);
    }

    @GetMapping("/{tradeId}")
    public Trade getTrade(@PathVariable(name = "accountId") final String accountId,
                          @PathVariable(name = "tradeId") final String tradeId) {
        return oandaTradeService.getTrade(accountId, tradeId);
    }

    @PutMapping("/{tradeId}/close")
    public HttpStatus closeTrade(@PathVariable(name = "accountId") final String accountId,
                                 @PathVariable(name = "tradeId") final String tradeId) {
        return oandaTradeService.closeTrade(accountId, tradeId);
    }
}
