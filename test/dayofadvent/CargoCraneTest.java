package test.dayofadvent;

import org.testng.annotations.Test;
import src.dayofadvent.CargoCrane;
import src.utils.FileReader;

import java.io.IOException;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static test.dayofadvent.TestConstant.FILE_PATH_FORMAT;


public class CargoCraneTest {

    static private final int DAY_OF_ADVENT = 5;

    @Test
    void getTopCratesAfterRearrangementType9000() throws IOException {
        assertEquals(
                CargoCrane.getStackTopsCraneType9000(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                "CMZ");
    }

    @Test
    void getTopCratesAfterRearrangementType9001() throws IOException {
        assertEquals(
                CargoCrane.getStackTopsCraneType9001(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                "MCD");
    }

    @Test
    public void testGetCratesForStack() {
        assertTrue(List.of('N', 'C', ' ').equals(CargoCrane.getListOfCrates("[N] [C]    ")));
        assertTrue(List.of('B', ' ', 'J', 'D', 'P', 'V', 'F', 'F').equals(
                        CargoCrane.getListOfCrates("[B]     [J] [D] [P] [V] [F] [F]")));
    }

    @Test
    public void testInitStack() {
        ArrayList<String> input = new ArrayList();
        input.add("    [D]");
        input.add("[N] [C]");
        input.add("[Z] [M] [P]");
        input.add(" 1   2   3 ");
        Map<Integer, LinkedList<Character>> actual = CargoCrane.initStacks(input);
        assertTrue(List.of(1, 2, 3).equals(actual.keySet().stream().toList()));
        assertTrue(actual.get(1).equals(List.of('Z', 'N')));
        assertTrue(actual.get(2).equals(List.of('M', 'C', 'D')));
        assertTrue(actual.get(3).equals(List.of('P')));
    }

    @Test
    public void testGetInstructions() {
        assertTrue(Arrays.equals(new int[]{1, 4, 1}, CargoCrane.getCraneAction("move 1 from 4 to 1").getInstruction()));
        assertTrue(Arrays.equals(new int[]{11, 1, 7}, CargoCrane.getCraneAction("move 11 from 1 to 7").getInstruction()));
    }
}