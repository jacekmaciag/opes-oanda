package pl.jdev.opes_oanda.domain.pricing;

import lombok.Data;

@Data
public class PricingHeartbeat {
    private String type;
    private String time;
}
