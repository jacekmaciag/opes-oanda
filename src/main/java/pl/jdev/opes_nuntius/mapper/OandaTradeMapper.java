package pl.jdev.opes_nuntius.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes_commons.domain.trade.Trade;
import pl.jdev.opes_nuntius.domain.trade.OandaTrade;

@Component
public class OandaTradeMapper extends AbstractOandaMapper<Trade, OandaTrade> {
    @Autowired
    private WebApplicationContext context;

    @Override
    public OandaTrade convertToOandaEntity(Trade object) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.createTypeMap(Trade.class, OandaTrade.class)
                .addMappings(typeMap -> {
                    typeMap.map(Trade::getExtId, OandaTrade::setId);
                });
        return mapper.map(object, OandaTrade.class);
    }

    @Override
    public Trade convertToEntity(OandaTrade oandaEntity) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.createTypeMap(OandaTrade.class, Trade.class)
                .addMappings(typeMap -> {
                    typeMap.map(OandaTrade::getId, Trade::setExtId);
                });
        return mapper.map(oandaEntity, Trade.class);
    }
}
