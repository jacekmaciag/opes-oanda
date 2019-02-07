package pl.jdev.opes_oanda.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_oanda.rest.service.OandaAccountService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private OandaAccountService oandaAccountService;

    @GetMapping
    @ResponseBody
    public List<Account> getAllAccounts() {
        return (List<Account>) oandaAccountService.getAllAccounts();
    }

    @GetMapping("/{accountId}")
    @ResponseBody
    public Account getAccount(@PathVariable final String accountId) throws IOException {
        return oandaAccountService.getAccount(accountId);
    }
}