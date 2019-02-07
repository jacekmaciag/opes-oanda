package pl.jdev.opes_oanda.domain.order.types.market_order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class MarketOrderTradeClose {
    private String tradeID;
    private String clientTradeID;
    private String units;
}
