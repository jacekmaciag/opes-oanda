package pl.jdev.opes_oanda.domain.pricing;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import pl.jdev.opes_commons.domain.pricing.QuoteHomeConversionFactors;

import java.util.Collection;

@Data
@TypeAlias("price")
public class Price {

    private String price;
    private String instrument;
    private String time;
    @Deprecated
    private PriceStatus status;
    private boolean tradeable;
    private Collection<PriceBucket> bids;
    private Collection<PriceBucket> asks;
    private String closeoutBid;
    private String closeoutAsk;
    @Deprecated
    private QuoteHomeConversionFactors quoteHomeConversionFactors;
    @Deprecated
    private UnitsAvailable unitsAvailable;
}
