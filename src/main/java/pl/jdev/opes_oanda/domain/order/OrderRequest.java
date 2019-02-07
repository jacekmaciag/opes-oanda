package pl.jdev.opes_oanda.domain.order;

import java.io.Serializable;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "type",
//        visible = true,
//        defaultImpl = MarketOrderRequest.class)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = MarketOrderRequest.class, name = "MARKET")
////        @JsonSubTypes.Type(value = Dog.class, name = "dog") //TODO: complete the list
//})
public class OrderRequest implements Serializable {
}