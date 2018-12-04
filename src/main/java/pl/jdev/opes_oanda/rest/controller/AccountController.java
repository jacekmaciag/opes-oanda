package pl.jdev.opes_oanda.rest.controller;

import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.rest.wrapper.JsonAccountListWrapper;
import pl.jdev.opes_commons.rest.wrapper.JsonAccountWrapper;
import pl.jdev.opes_oanda.rest.service.OandaAccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController extends AbstractEntityController<Account> {
    @GetMapping
    @ResponseBody
    public JsonAccountListWrapper getAllAccounts() {
        return JsonAccountListWrapper.payloadOf(((OandaAccountService) entityService).getAllAccounts());
    }

    @GetMapping
    @ResponseBody
    public JsonAccountWrapper getAccount(@PathVariable final String accountId) {
        return JsonAccountWrapper.payloadOf(((OandaAccountService) entityService).getAccount(accountId));
    }
}
