package pl.jdev.opes_oanda.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_oanda.domain.account.OandaAccount;

@Component
public class OandaAccountMapper extends AbstractOandaMapper<Account, OandaAccount> {
    @Autowired
    private WebApplicationContext context;

    @Override
    public OandaAccount convertToOandaEntity(Account object) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.createTypeMap(Account.class, OandaAccount.class)
                .addMappings(typeMap -> {
                    typeMap.map(Account::getExtId, OandaAccount::setId);
                });
        return mapper.map(object, OandaAccount.class);
    }

    @Override
    public Account convertToEntity(OandaAccount oandaEntity) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.createTypeMap(OandaAccount.class, Account.class)
                .addMappings(typeMap -> {
                    typeMap.map(OandaAccount::getId, Account::setExtId);
                });
        return mapper.map(oandaEntity, Account.class);
    }
}
