package pl.jdev.opes_oanda.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;
import java.util.Date;

@Data
//@Builder
//@JsonRootName("account")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OandaAccount {
    private String id;
    private String alias;
    private Currency currency;
    private Double balance;
    private String createdByUserID;
    private Date createdTime;
    private GuaranteedStopLossOrderMode guaranteedStopLossOrderMode;
    private Double pl;
    private Double resettablePL;
    private Date resettablePLTime;
    private Double financing;
    private Double commission;
    private Double guaranteedExecutionFees;
    private Double marginRate;
    private Date marginCallEnterTime;
    private Integer marginCallExtensionCount;
    private Date lastMarginCallExtensionTime;
    private Integer openTradeCount;
    private Integer openPositionCount;
    private Integer pendingOrderCount;
    private Boolean hedgingEnabled;
    private Double unrealizedPL;
    private Double nav;
    private Double marginUsed;
    private Double marginAvailable;
    private Double positionValue;
    private Double marginCloseoutUnrealizedPL;
    private Double marginCloseoutNAV;
    private Double marginCloseoutMarginUsed;
    private Double marginCloseoutPercent;
    private Double marginCloseoutPositionValue;
    private Double withdrawalLimit;
    private Double marginCallMarginUsed;
    private Double marginCallPercent;
}