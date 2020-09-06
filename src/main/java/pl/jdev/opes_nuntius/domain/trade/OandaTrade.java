package pl.jdev.opes_nuntius.domain.trade;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import pl.jdev.opes_commons.domain.ClientExtensions;

import java.util.Date;
import java.util.List;

@Data
@TypeAlias("trade")
public class OandaTrade {

    public enum TradeState {
        OPEN, CLOSED, CLOSE_WHEN_TRADEABLE;
    }

    private String id;
    private String instrument;
    private Double price;
    private Date openTime;
    private TradeState state;
    private Double initialUnits;
    private Double initialMarginRequired;
    private Double currentUnits;
    private Double realizedPL;
    private Double unrealizedPL;
    private Double marginUsed;
    private Double averageClosePrice;
    private List<String> closingTransactionIDs;
    private Double financing;
    private Date closeTime;
    private ClientExtensions clientExtensions;
    // private OandaOrder takeProfitOrder;
    // private OandaOrder stopLossOrder;
    // private OandaOrder trailingStopLossOrder;

    public OandaTrade() {

    }

}