package pl.jdev.opes_oanda.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jdev.opes_commons.domain.pricing.Price;

@RestController
@RequestMapping("/pricing")
public class PricingController extends AbstractEntityController<Price> {
//    @PostMapping
//    @ResponseBody
}
