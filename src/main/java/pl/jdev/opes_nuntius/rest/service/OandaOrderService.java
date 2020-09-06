package pl.jdev.opes_nuntius.rest.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jdev.opes_commons.domain.order.Order;
import pl.jdev.opes_commons.rest.message.response.JsonOrderListWrapper;
import pl.jdev.opes_nuntius.config.Url;
import pl.jdev.opes_nuntius.domain.order.types.market_order.MarketOrder;
import pl.jdev.opes_nuntius.mapper.OandaOrderMapper;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;
import static pl.jdev.opes_nuntius.rest.service.OandaResponseHandler.unpack;

@Component
@Log4j2(topic = "CORE - OandaOrder")
public class OandaOrderService extends AbstractOandaService {

    @Autowired
    private OandaOrderMapper mapper;

    @Autowired
    public OandaOrderService(RestTemplate restTemplate,
                             Url url) {
        super(restTemplate, url);
    }

    public HttpStatus postOrder(String accountId, Order orderCreateRequest) {
        return this.restTemplate
                .exchange(UriComponentsBuilder.newInstance()
                                .scheme(ReferenceUriSchemesSupported.HTTPS.toString())
                                .host(url.OANDA_HOST)
                                .path(url.ORDER_LIST_URL)
                                .buildAndExpand(accountId)
                                .toString(),
                        POST,
                        new HttpEntity<>(Map.of("order", orderCreateRequest), null),
                        String.class)
                .getStatusCode();
    }

    /**
     * Returns list of all orders for the provided accountId.
     *
     * @return list of orders for provided accountId
     */
    public List<Order> getAllOrders(String accountId) {
        return ((List<MarketOrder>) unpack(this.restTemplate
                        .exchange(UriComponentsBuilder.newInstance()
                                        .scheme(ReferenceUriSchemesSupported.HTTPS.toString())
                                        .host(url.OANDA_HOST)
                                        .path(url.ORDER_LIST_URL)
                                        .buildAndExpand(accountId)
                                        .toString(),
                                GET,
                                new HttpEntity<>(null, null),
                                String.class)
                        .getBody(),
                MarketOrder.class,
                "orders"))
                .stream()
//                .map(order -> (MarketOrder) order)
                .map(mapper::convertToEntity)
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
        return mapper.convertToEntity(
                (MarketOrder) unpack(this.restTemplate
                                .exchange(UriComponentsBuilder.newInstance()
                                                .scheme(ReferenceUriSchemesSupported.HTTPS.toString())
                                                .host(url.OANDA_HOST)
                                                .path(url.SINGLE_ORDER_URL)
                                                .buildAndExpand(accountId, orderId)
                                                .toString(),
                                        GET,
                                        new HttpEntity<>(EMPTY, null),
                                        String.class)
                                .getBody(),
                        MarketOrder.class,
                        "order"));
    }

    public Order replaceOrder(String accountId, String existingOrderSpecifier, Order replacingOrder) {
        return this.restTemplate
                .exchange(fromPath(null)
                                .buildAndExpand(accountId, existingOrderSpecifier)
                                .toString(),
                        PUT,
                        new HttpEntity<>(EMPTY, null),
                        Order.class)
                .getBody();
    }

    public HttpStatus cancelOrder(String accountId, String orderSpecifier) {
        return this.restTemplate
                .exchange(UriComponentsBuilder.newInstance()
                                .scheme(ReferenceUriSchemesSupported.HTTPS.toString())
                                .host(url.OANDA_HOST)
                                .path(url.CANCEL_ORDER_URL)
                                .buildAndExpand(accountId, orderSpecifier)
                                .toString(),
                        PUT,
                        new HttpEntity<>(null, null),
                        String.class)
                .getStatusCode();
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
