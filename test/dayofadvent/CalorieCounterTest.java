package test.dayofadvent;

import org.testng.annotations.Test;
import src.dayofadvent.CalorieCounter;
import src.utils.FileReader;

import java.io.IOException;

import static org.testng.Assert.*;
import static test.dayofadvent.TestConstant.FILE_PATH_FORMAT;

public class CalorieCounterTest {

    static private final int DAY_OF_ADVENT = 1;

    @Test
    public void testCalcMaxCaloriesCarried() throws IOException {
        assertEquals(24000,
                CalorieCounter.calcMaxCaloriesCarried(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)));
    }

    @Test
    public void testCalcCaloriesCarriedByTopThree() throws IOException {
        assertEquals(45000,
                CalorieCounter.calcCaloriesCarriedByTopThree(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)));
    }
}
