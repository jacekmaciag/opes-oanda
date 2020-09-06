package pl.jdev.opes_nuntius.rest.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jdev.opes_commons.domain.order.Order;
import pl.jdev.opes_commons.rest.json.OrderViews;
import pl.jdev.opes_nuntius.rest.service.OandaOrderService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/orders")
public class OrderController {
    @Autowired
    private OandaOrderService oandaOrderService;

    @GetMapping
    @ResponseBody
    public List<Order> getAllOrders(@PathVariable(name = "accountId") final String accountId) {
        return oandaOrderService.getAllOrders(accountId);
    }

    @GetMapping("/{orderId}")
    @ResponseBody
    public Order getOrder(@PathVariable(name = "accountId") final String accountId,
                          @PathVariable(name = "orderId") final String orderId) {
        return oandaOrderService.getOrder(accountId, orderId);
    }

    @PostMapping
    @ResponseBody
    public HttpStatus createOrder(@PathVariable(name = "accountId") final String accountId,
                                  @JsonView(OrderViews.OandaCreate.class) @Valid @RequestBody final Order createOrderRequest) {
        return oandaOrderService.postOrder(accountId, createOrderRequest);
    }

    @PutMapping("/{orderId}/cancel")
    public HttpStatus cancelOrder(@PathVariable(name = "accountId") final String accountId,
                                  @PathVariable(name = "orderId") final String orderId) {
        return oandaOrderService.cancelOrder(accountId, orderId);
    }
}