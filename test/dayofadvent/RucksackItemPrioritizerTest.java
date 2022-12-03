package test.dayofadvent;

import org.testng.annotations.Test;
import src.dayofadvent.RucksackItemPrioritizer;
import src.utils.FileReader;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static test.dayofadvent.TestConstant.FILE_PATH_FORMAT;

public class RucksackItemPrioritizerTest {

    static private final int DAY_OF_ADVENT = 3;

    @Test
    public void testGetItemPrioritySum() throws IOException {
        assertEquals(
                RucksackItemPrioritizer.getItemPrioritySum(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                157);
    }

    @Test
    public void testGetGroupPrioritySum() throws IOException {
        assertEquals(
                RucksackItemPrioritizer.getGroupPrioritySum(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                70);
    }

}