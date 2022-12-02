package src.dayofadvent;

import src.utils.FileReader;

import java.io.IOException;
import java.util.List;

import static src.dayofadvent.RockPaperScissorRule.getPlayerOutcome;
import static src.dayofadvent.RockPaperScissorRule.suggestPlayerAction;

public class StrategyGuideEvaluator {

    public static int calcScoreByPlayerAction(String strategyGuideFilename) throws IOException, IllegalAccessException {
        List<String> strategyList = FileReader.readFileToLines(strategyGuideFilename);

        int totalStrategyScore = 0;
        for (String strategy : strategyList) {
            HandShape opponentHand = getHandShapeFromStrategy(strategy, 0);
            HandShape playerHand = getHandShapeFromStrategy(strategy, 1);
            totalStrategyScore += getScorePlayerAgainstOpponent(playerHand, opponentHand) + playerHand.getHandScore();
        }

        return totalStrategyScore;
    }

    public static int calcScoreByPlayerOutcome(String strategyGuideFilename) throws IOException, IllegalAccessException {
        List<String> strategyList = FileReader.readFileToLines(strategyGuideFilename);

        int totalStrategyScore = 0;
        for (String strategy : strategyList) {
            HandShape opponentHand = getHandShapeFromStrategy(strategy, 0);
            String outcome = splitString(strategy)[1];
            HandShape playerHand = suggestPlayerAction(opponentHand, RoundOutcome.valueOfOutcomeSymbol(outcome));

            totalStrategyScore += getScorePlayerAgainstOpponent(playerHand, opponentHand) + playerHand.getHandScore();
        }
        return totalStrategyScore;
    }

    private static String[] splitString(String input){
        return input.split(" ");
    }

    private static HandShape getHandShapeFromStrategy(String strategy, int index) throws IllegalAccessException {
        return HandShape.valueOfHandSymbol(splitString(strategy)[index]);
    }

    private static int getScorePlayerAgainstOpponent(HandShape playerHand, HandShape opponentHand) {
        return getPlayerOutcome(playerHand, opponentHand).getOutcomeScore();
    }

}
