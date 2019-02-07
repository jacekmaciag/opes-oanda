package pl.jdev.opes_oanda.domain.order.types.market_order;

import lombok.Builder;
import lombok.Data;
import pl.jdev.opes_commons.domain.ClientExtensions;
import pl.jdev.opes_commons.domain.order.*;
import pl.jdev.opes_commons.domain.order.types.market_order.MarketOrderMarginCloseout;
import pl.jdev.opes_commons.domain.order.types.market_order.MarketOrderPositionCloseout;
import pl.jdev.opes_oanda.domain.order.OandaOrder;

import java.util.Date;
import java.util.List;

@Data
public class MarketOrder extends OandaOrder {
    private OrderType type;
    private String instrument;
    private String units;
    private TimeInForce timeInForce;
    private String priceBound;
    private OrderPositionFill positionFill;
    private MarketOrderTradeClose tradeClose;
    private MarketOrderPositionCloseout longPositionCloseout;
    private MarketOrderPositionCloseout shortPositionCloseout;
    private MarketOrderMarginCloseout marginCloseout;
    private MarketOrderDelayedTradeClose delayedTradeClose;
    private FillingOrderDetails takeProfitOnFill;
    private FillingOrderDetails stopLossOnFill;
    private FillingOrderDetails trailingStopLossOnFill;
    private ClientExtensions tradeClientExtensions;
    private String fillingTransactionID;
    private Date filledTime;
    private String tradeOpenedID;
    private String tradeReducedID;
    private List<String> tradeClosedIDs;
    private String cancellingTransactionID;
    private Date cancelledTime;

    @Builder
    public MarketOrder(String id,
                       Date createTime,
                       OrderState state,
                       ClientExtensions clientExtensions,
                       String instrument,
                       String units,
                       TimeInForce timeInForce,
                       String priceBound,
                       OrderPositionFill positionFill,
                       MarketOrderTradeClose tradeClose,
                       MarketOrderPositionCloseout longPositionCloseout,
                       MarketOrderPositionCloseout shortPositionCloseout,
                       MarketOrderMarginCloseout marginCloseout,
                       MarketOrderDelayedTradeClose delayedTradeClose,
                       FillingOrderDetails takeProfitOnFill,
                       FillingOrderDetails stopLossOnFill,
                       FillingOrderDetails trailingStopLossOnFill,
                       ClientExtensions tradeClientExtensions,
                       String fillingTransactionID,
                       Date filledTime,
                       String tradeOpenedID,
                       String tradeReducedID,
                       List<String> tradeClosedIDs,
                       String cancellingTransactionID,
                       Date cancelledTime) {
        super(id, createTime, state, clientExtensions);
        this.type = OrderType.MARKET;
        this.instrument = instrument;
        this.units = units;
        this.timeInForce = timeInForce;
        this.priceBound = priceBound;
        this.positionFill = positionFill;
        this.tradeClose = tradeClose;
        this.longPositionCloseout = longPositionCloseout;
        this.shortPositionCloseout = shortPositionCloseout;
        this.marginCloseout = marginCloseout;
        this.delayedTradeClose = delayedTradeClose;
        this.takeProfitOnFill = takeProfitOnFill;
        this.stopLossOnFill = stopLossOnFill;
        this.trailingStopLossOnFill = trailingStopLossOnFill;
        this.tradeClientExtensions = tradeClientExtensions;
        this.fillingTransactionID = fillingTransactionID;
        this.filledTime = filledTime;
        this.tradeOpenedID = tradeOpenedID;
        this.tradeReducedID = tradeReducedID;
        this.tradeClosedIDs = tradeClosedIDs;
        this.cancellingTransactionID = cancellingTransactionID;
        this.cancelledTime = cancelledTime;
    }

    @Builder(builderMethodName = "requestBuilder")
    public MarketOrder(ClientExtensions clientExtensions,
                       String instrument,
                       String units,
                       TimeInForce timeInForce,
                       String priceBound,
                       OrderPositionFill positionFill,
                       FillingOrderDetails takeProfitOnFill,
                       FillingOrderDetails stopLossOnFill,
                       FillingOrderDetails trailingStopLossOnFill,
                       ClientExtensions tradeClientExtensions) {
        super(null, null, null, clientExtensions);
        this.type = OrderType.MARKET;
        this.instrument = instrument;
        this.units = units;
        this.timeInForce = timeInForce;
        this.priceBound = priceBound;
        this.positionFill = positionFill;
        this.takeProfitOnFill = takeProfitOnFill;
        this.stopLossOnFill = stopLossOnFill;
        this.trailingStopLossOnFill = trailingStopLossOnFill;
        this.tradeClientExtensions = tradeClientExtensions;
    }
}
