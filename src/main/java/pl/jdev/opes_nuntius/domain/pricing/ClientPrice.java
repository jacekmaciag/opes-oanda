package pl.jdev.opes_nuntius.domain.pricing;

import lombok.Data;

import java.util.Collection;

@Data
public class ClientPrice {
    private Collection<PriceBucket> bids;
    private Collection<PriceBucket> asks;
    private String closeoutBid;
    private String closeoutAsk;
    private String timestamp;
}
