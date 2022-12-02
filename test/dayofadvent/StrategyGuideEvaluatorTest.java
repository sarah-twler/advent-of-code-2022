package test.dayofadvent;

import org.testng.annotations.Test;
import src.dayofadvent.StrategyGuideEvaluator;
import src.utils.FileReader;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static test.dayofadvent.TestConstant.FILE_PATH_FORMAT;

public class StrategyGuideEvaluatorTest {

    static private final int DAY_OF_ADVENT = 2;

    @Test
    public void testCalcStrategyScore() throws IOException, IllegalAccessException {
        assertEquals(15,
                StrategyGuideEvaluator.calcScoreByPlayerAction(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)));
    }

    @Test
    public void testCalcScoreByPlayerOutcome() throws IOException, IllegalAccessException {
        assertEquals(12,
                StrategyGuideEvaluator.calcScoreByPlayerOutcome(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)));
    }

}