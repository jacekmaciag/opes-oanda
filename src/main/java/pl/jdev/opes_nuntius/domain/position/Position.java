package pl.jdev.opes_nuntius.domain.position;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import pl.jdev.opes_commons.domain.instrument.Instrument;

@Data
@TypeAlias("position")
public class Position {
    private Instrument instrument;
    private Double pl;
    private Double unrealizedPL;
    private Double resettablePL;
    private Double commission;
    private PositionSide longPosition;
    private PositionSide shortPosition;
}
