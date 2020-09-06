package pl.jdev.opes_nuntius.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import pl.jdev.opes_commons.domain.order.Order;
import pl.jdev.opes_nuntius.domain.order.OandaOrder;

@Component
public class OandaOrderMapper extends AbstractOandaMapper<Order, OandaOrder> {
    @Autowired
    private WebApplicationContext context;

    @Override
    public OandaOrder convertToOandaEntity(Order object) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.createTypeMap(Order.class, OandaOrder.class)
                .addMappings(typeMap -> {
                    typeMap.map(Order::getExtId, OandaOrder::setOrderId);
                });
        return mapper.map(object, OandaOrder.class);
    }

    @Override
    public Order convertToEntity(OandaOrder oandaEntity) {
        ModelMapper mapper = context.getBean(ModelMapper.class);
        mapper.createTypeMap(OandaOrder.class, Order.class)
                .addMappings(typeMap -> {
                    typeMap.map(OandaOrder::getOrderId, Order::setExtId);
                });
        return mapper.map(oandaEntity, Order.class);
    }
}
