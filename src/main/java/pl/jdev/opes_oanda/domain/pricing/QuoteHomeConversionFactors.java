package pl.jdev.opes_oanda.domain.pricing;

import lombok.Data;

@Data
public class QuoteHomeConversionFactors {
    private double positiveUnits;
    private double negativeUnits;
}
