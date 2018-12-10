package pl.jdev.opes_oanda.rest.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.rest.message.response.JsonAccountListWrapper;
import pl.jdev.opes_commons.rest.message.response.JsonAccountWrapper;
import pl.jdev.opes_oanda.config.Url;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@Log4j2(topic = "CORE - Account")
public class OandaAccountService extends AbstractOandaService<Account> {

    @Autowired
    public OandaAccountService(RestTemplate restTemplate,
                               Url url) {
        super(restTemplate, url);
    }

    /**
     * Returns all accounts and their details.
     *
     * @return all accounts with details
     */
    public Collection<Account> getAllAccounts() {
        Collection<Account> accountList = new ArrayList<>();
        this.restTemplate
                .exchange(url.ACCOUNT_LIST_URL,
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        JsonAccountListWrapper.class)
                .getBody()
                .getAccounts()
                .forEach(account -> {
                    accountList.add(getAccount(account.getAccountId()));
                });
        return accountList;
    }

    /**
     * Returns accounts and its details for the provided accountId.
     *
     * @return accounts with details for provided id
     */
    public Account getAccount(String accountId) {
        return this.restTemplate
                .exchange(fromPath(url.SINGLE_ACCOUNT_URL)
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        JsonAccountWrapper.class)
                .getBody()
                .getAccount();
    }
}
