package test.dayofadvent;

import org.testng.annotations.Test;
import src.dayofadvent.MonkeyPredictor;
import src.utils.FileReader;

import java.io.IOException;
import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static test.dayofadvent.TestConstant.FILE_PATH_FORMAT;

public class MonkeyPredictorTest {

    static private final int DAY_OF_ADVENT = 11;

    @Test
    public void testGetMonkeyBusinessLevelLow() throws IOException {
        assertEquals(
                MonkeyPredictor.getMonkeyBusinessLevelLow(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                10605);
    }

    @Test
    public void testGetMonkeyBusinessLevelHigh() throws IOException {
//        assertEquals(
//                MonkeyPredictor.getMonkeyBusinessLevelHigh(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
//                2713310158);
    }

}