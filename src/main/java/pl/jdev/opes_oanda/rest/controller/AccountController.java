package pl.jdev.opes_oanda.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.rest.message.response.JsonAccountListWrapper;
import pl.jdev.opes_commons.rest.message.response.JsonAccountWrapper;
import pl.jdev.opes_oanda.rest.service.OandaAccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController extends AbstractEntityController<Account> {
    @Autowired
    OandaAccountService oandaAccountService;

    @GetMapping
    @ResponseBody
    public JsonAccountListWrapper getAllAccounts() {
        return JsonAccountListWrapper.payloadOf(oandaAccountService.getAllAccounts());
    }

    @GetMapping("/{accountId}")
    @ResponseBody
    public JsonAccountWrapper getAccount(@PathVariable final String accountId) {
        return JsonAccountWrapper.payloadOf(oandaAccountService.getAccount(accountId));
    }
}
