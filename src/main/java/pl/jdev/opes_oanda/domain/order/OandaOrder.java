package pl.jdev.opes_oanda.domain.order;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import pl.jdev.opes_commons.domain.ClientExtensions;
import pl.jdev.opes_commons.domain.order.OrderState;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonRootName("order")
public abstract class OandaOrder {
    private String orderId;
    private Date createTime;
    private OrderState state;
    private ClientExtensions clientExtensions;
}