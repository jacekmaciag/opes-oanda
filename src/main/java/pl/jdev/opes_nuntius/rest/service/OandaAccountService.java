package pl.jdev.opes_nuntius.rest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.domain.broker.BrokerName;
import pl.jdev.opes_nuntius.config.Url;
import pl.jdev.opes_nuntius.domain.account.OandaAccount;
import pl.jdev.opes_nuntius.mapper.OandaAccountMapper;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.GET;
import static pl.jdev.opes_nuntius.rest.service.OandaResponseHandler.unpack;

@Component
//@Log4j2(topic = "CORE - OandaAccount")
public class OandaAccountService extends AbstractOandaService {
    @Autowired
    private OandaAccountMapper mapper;

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
        Collection<OandaAccount> accountList = this.restTemplate
                .exchange(UriComponentsBuilder.newInstance()
                                .scheme(ReferenceUriSchemesSupported.HTTPS.toString())
                                .host(url.OANDA_HOST)
                                .path(url.ACCOUNT_LIST_URL)
                                .build()
                                .toString(),
                        GET,
                        null,
                        Collection.class)
                .getBody();
        return accountList.stream()
                .map(entity -> mapper.convertToEntity(entity))
                .map(this::labelAccount)
                .collect(Collectors.toList());
    }

    /**
     * Returns accounts and its details for the provided accountId.
     *
     * @return accounts with details for provided id
     */
    public Account getAccount(String accountId) {
        ResponseEntity<String> responseEntity = this.restTemplate
                .exchange(UriComponentsBuilder.newInstance()
                                .scheme(ReferenceUriSchemesSupported.HTTPS.toString())
                                .host(url.OANDA_HOST)
                                .path(url.SINGLE_ACCOUNT_URL)
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        null,
                        String.class);
        OandaAccount oandaAccount = (OandaAccount) unpack(responseEntity.getBody(), OandaAccount.class, "account");
        Account account = mapper.convertToEntity(oandaAccount);
        return labelAccount(account);
    }

    private Account labelAccount(Account account) {
        account.setBroker(BrokerName.OANDA);
        return account;
    }

    private Object parseToEntity(String responseBody) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = null;
        OandaAccount account = null;
        try {
            json = mapper.readTree(responseBody);
            long lastTransactionId = json.get("lastTransactionID").asLong();
            JsonNode accountNode = json.get("account");
            account = mapper.treeToValue(accountNode, OandaAccount.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }
}