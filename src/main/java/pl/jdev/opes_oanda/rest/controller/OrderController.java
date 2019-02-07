package pl.jdev.opes_oanda.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.order.Order;
import pl.jdev.opes_commons.rest.HttpHeaders;
import pl.jdev.opes_commons.rest.IntegrationClient;
import pl.jdev.opes_commons.rest.message.CancelOrderAction;
import pl.jdev.opes_commons.rest.message.CreateOrderAction;
import pl.jdev.opes_commons.rest.message.request.EntityDetailsRequest;
import pl.jdev.opes_commons.rest.message.response.JsonOrderListWrapper;
import pl.jdev.opes_commons.rest.message.response.JsonOrderWrapper;
import pl.jdev.opes_oanda.rest.service.OandaOrderService;

import javax.validation.Valid;
import java.util.UUID;

import static pl.jdev.opes_commons.rest.HttpHeaders.DATA_TYPE;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OandaOrderService oandaOrderService;
    @Autowired
    IntegrationClient integrationClient;

    @PostMapping
    public void createOrder(@Valid @RequestBody final CreateOrderAction createOrderAction) {
//oandaOrderService.postOrder(createOrderRequest)
    }

    @PutMapping
    public void cancelOrder(@Valid @RequestBody final CancelOrderAction cancelOrderAction) {
        oandaOrderService.cancelOrder(cancelOrderAction.getExtAccountId(), cancelOrderAction.getExtOrderId());
    }

    @GetMapping
    @ResponseBody
    public JsonOrderListWrapper getAllOrders() {

//        oandaOrderService.getAllOrders();
        return null;
    }

    @GetMapping("/{orderId}")
    @ResponseBody
    public JsonOrderWrapper getOrder(@PathVariable(name = "orderId") final UUID orderId) {
        //TODO: grab extId from db
        HttpHeaders headers = new HttpHeaders();
        headers.add(DATA_TYPE, "order");
        return JsonOrderWrapper.payloadOf(
                (Order) integrationClient.requestData(
                        new EntityDetailsRequest(orderId),
                        Order.class
                ).getBody()
        );
    }
}