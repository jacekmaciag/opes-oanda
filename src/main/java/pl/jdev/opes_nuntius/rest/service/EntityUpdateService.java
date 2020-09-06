package pl.jdev.opes_nuntius.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.jdev.opes_commons.rest.client.IntegrationClient;

import static java.util.stream.Collectors.toMap;

@Service
public class EntityUpdateService {
    @Autowired
    private IntegrationClient integrationClient;
    @Autowired
    private OandaAccountService accountService;

    @Async
    private void processLastTransaction(long lastTransaction) {
        System.out.println("lastTransactionID: " + lastTransaction);
    }

//    @Scheduled(fixedRateString = "${opes.service.entity_update.account_interval}")
//    private void updateAccountChanges() {
//        EntityDetailsRequest oandaAccountsRequest = new EntityDetailsRequest(null);
//        List<Account> accounts = (List<Account>) integrationClient
//                .requestData(oandaAccountsRequest, List.class)
//                .getBody();
//        Map<UUID, Account> accountMap = accounts.stream()
//                .collect(toMap(Account::getId, account -> account));
//        accounts.parallelStream()
//                .map(Account::getExtId)
//                .map(accountService::getAccount)
//                .filter(oandaAccount -> !oandaAccount.equals(accountMap.get(oandaAccount.getId())))
//                .map(AccountUpdatedEvent::new)
//                .forEach(integrationClient::postEvent);
//    }
}