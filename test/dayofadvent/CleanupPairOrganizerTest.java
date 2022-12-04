package test.dayofadvent;

import org.testng.annotations.Test;
import src.dayofadvent.CleanupPairOrganizer;
import src.utils.FileReader;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static test.dayofadvent.TestConstant.FILE_PATH_FORMAT;

public class CleanupPairOrganizerTest {

    static private final int DAY_OF_ADVENT = 4;

    @Test
    public void testGetSumOfPairsFullyContained() throws IOException {
        assertEquals(
                CleanupPairOrganizer.getSumOfPairsFullyContained(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                2);
    }

    @Test
    public void testGetSumOfPairsOverlapped() throws IOException {
        assertEquals(
                CleanupPairOrganizer.getSumOfPairsOverlapped(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                4);
    }

}