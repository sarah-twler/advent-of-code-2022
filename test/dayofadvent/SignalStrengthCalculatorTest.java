package test.dayofadvent;

import org.testng.annotations.Test;
import src.dayofadvent.SignalStrengthCalculator;
import src.utils.FileReader;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static test.dayofadvent.TestConstant.FILE_PATH_FORMAT;

public class SignalStrengthCalculatorTest {

    static private final int DAY_OF_ADVENT = 10;

    @Test
    public void testGetSumSignalStrength() throws IOException {
        assertEquals(
                SignalStrengthCalculator.getSumSignalStrength(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                13140);
    }

    @Test
    public void testGetCrtScreen() throws IOException {
        String expected =
                "##..##..##..##..##..##..##..##..##..##..\n" +
                "###...###...###...###...###...###...###.\n" +
                "####....####....####....####....####....\n" +
                "#####.....#####.....#####.....#####.....\n" +
                "######......######......######......####\n" +
                "#######.......#######.......#######.....\n";
        String actual = SignalStrengthCalculator.getCrtScreen(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT));
        assertEquals(actual, expected);
    }


}