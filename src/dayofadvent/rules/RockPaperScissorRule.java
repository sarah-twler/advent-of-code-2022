package src.dayofadvent.rules;

import src.dayofadvent.enums.HandShape;
import src.dayofadvent.enums.RoundOutcome;

import java.util.*;

import static src.dayofadvent.enums.RoundOutcome.*;

public class RockPaperScissorRule {

    private static final Map<HandShape, HandShape> winMap;
    static {
        Map<HandShape, HandShape> map = new HashMap();
        map.put(HandShape.ROCK, HandShape.SCISSOR);
        map.put(HandShape.SCISSOR, HandShape.PAPER);
        map.put(HandShape.PAPER, HandShape.ROCK);
        winMap = Collections.unmodifiableMap(map);
    }

    public static RoundOutcome getPlayerOutcome(HandShape playerHand, HandShape opponentHand) {
        if (playerHand.compareTo((opponentHand)) == 0)
            return DRAW;
        if (winMap.get(playerHand).equals(opponentHand))
            return WIN;
        if (winMap.get(opponentHand).equals(playerHand))
            return LOOSE;

        return null;
    }

    public static HandShape suggestPlayerAction(HandShape opponentHand, RoundOutcome roundOutcome) {
        if (DRAW.equals(roundOutcome))
            return opponentHand;
        if (LOOSE.equals(roundOutcome))
            return winMap.get(opponentHand);
        if (WIN.equals(roundOutcome))
            return winMap.keySet().stream().filter(k -> winMap.get(k).equals(opponentHand)).findFirst()
                    .orElse(null);
        return null;
    }
}
