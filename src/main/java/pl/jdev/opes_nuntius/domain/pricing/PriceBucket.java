package pl.jdev.opes_nuntius.domain.pricing;

import lombok.Data;

@Data
class PriceBucket {
    private String price;
    private Integer liquidity;
}
