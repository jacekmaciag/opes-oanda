package pl.jdev.opes_oanda.rest.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.jdev.opes_commons.domain.order.Order;
import pl.jdev.opes_commons.domain.order.OrderRequest;
import pl.jdev.opes_commons.rest.message.response.JsonOrderListWrapper;
import pl.jdev.opes_oanda.config.Url;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@Component
@Log4j2(topic = "CORE - Order")
public class OandaOrderService extends AbstractOandaService<Order> {
    @Autowired
    public OandaOrderService(RestTemplate restTemplate,
                             Url url) {
        super(restTemplate, url);
    }

    public Order postOrder(String accountId, OrderRequest orderRequest) {
        return this.restTemplate
                .exchange(fromPath(url.ORDER_LIST_URL)
                                .buildAndExpand(accountId)
                                .toString(),
                        POST,
                        new HttpEntity<>(Map.of("order", orderRequest), null),
                        Order.class)
                .getBody();
    }

    /**
     * Returns list of all orders for the provided accountId.
     *
     * @return list of orders for provided accountId
     */
    public List<Order> getAllOrders(String accountId) {
        return this.restTemplate
                .exchange(fromPath(url.ORDER_LIST_URL)
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        JsonOrderListWrapper.class)
                .getBody()
                .getOrders()
                .stream()
//                .map(order -> repository.upsert(order.getOrderId(), order))
                .collect(toList());
    }

    /**
     * Returns list of all pending orders for the provided accountId.
     *
     * @return list of pending orders for provided accountId
     */
    public List<Order> getPendingOrders(String accountId) {
        return this.restTemplate
                .exchange(fromPath(url.ORDER_LIST_URL)
                                .buildAndExpand(accountId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        JsonOrderListWrapper.class)
                .getBody()
                .getOrders()
                .stream()
                .collect(toList());
    }

    public Order getOrder(String accountId, String orderId) {
        return this.restTemplate
                .exchange(fromPath(url.SINGLE_ORDER_URL)
                                .buildAndExpand(accountId, orderId)
                                .toString(),
                        GET,
                        new HttpEntity<>(EMPTY, null),
                        Order.class)
                .getBody();
    }

    public Order replaceOrder(String accountId, String existingOrderSpecifier, OrderRequest replacingOrder) {
        return this.restTemplate
                .exchange(fromPath(null)
                                .buildAndExpand(accountId, existingOrderSpecifier)
                                .toString(),
                        PUT,
                        new HttpEntity<>(EMPTY, null),
                        Order.class)
                .getBody();
    }

    public Order cancelOrder(String accountId, String orderSpecifier) {
        return this.restTemplate
                .exchange(fromPath(url.CANCEL_ORDER_URL)
                                .buildAndExpand(accountId, orderSpecifier)
                                .toString(),
                        PUT,
                        new HttpEntity<>(EMPTY, null),
                        Order.class)
                .getBody();
    }

    public Order updateOrderClientExtensions(String accountId, String orderSpecifier) {
        return this.restTemplate
                .exchange(fromPath(url.ORDER_CLIENT_EXT_URL)
                                .buildAndExpand(accountId, orderSpecifier)
                                .toString(),
                        PUT,
                        new HttpEntity<>(EMPTY, null),
                        Order.class)
                .getBody();
    }
}
