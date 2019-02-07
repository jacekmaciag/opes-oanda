package pl.jdev.opes_oanda.domain.instrument;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import pl.jdev.opes_commons.domain.instrument.CandlestickData;

@Data
@Builder
@TypeAlias("candle")
public class Candlestick {
    private String time;
    private CandlestickData bid;
    private CandlestickData ask;
    private CandlestickData mid;
    private int volume;
    private boolean complete;
}
