package pl.jdev.opes_nuntius.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_nuntius.rest.service.OandaAccountService;

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
    public Account getAccount(@PathVariable final String accountId) {
        return oandaAccountService.getAccount(accountId);
    }

    @PostMapping
    public Account accountQuery(@RequestBody final Account account) {
        return oandaAccountService.getAccount(account.getId().toString());
    }
}