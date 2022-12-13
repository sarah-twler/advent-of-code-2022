package test.dayofadvent;


import org.testng.annotations.Test;
import src.dayofadvent.Rope;
import src.dayofadvent.RopeTailTracer;
import src.utils.FileReader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static test.dayofadvent.TestConstant.FILE_PATH_FORMAT;

public class RopeTailTracerTest {

    static private final int DAY_OF_ADVENT = 9;

    @Test
    public void testCountTailPositions() throws IOException {
        assertEquals(
                new RopeTailTracer().countTailPositions(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                13);
    }

    @Test
    public void testCountTailPositionsRope() throws IOException {
        assertEquals(
                new RopeTailTracer().countTailPositionsRope(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                1);
    }

    @Test
    public void testProcessSteps() {
        assertEquals(
                new RopeTailTracer().processSteps(
                    List.of("R 5", "U 5", "L 5", "D 5", "R 5", "U 5", "L 5", "D 5")),
                17);
    }

    @Test
    public void testRopePosition() throws IOException {
        RopeTailTracer tracer = new RopeTailTracer();
        tracer.countTailPositionsRope(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT));
        LinkedList<String> history = tracer.getRopeHistory();

        assertEquals(history.get(0), new Rope(
                new int[][]{{4,0}, {3,0}, {2,0}, {1,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0} }).toString());
        assertEquals(history.get(1), new Rope(
                new int[][] {{4, 4}, {4,3}, {4,2}, {3,2}, {2,2}, {1,1}, {0,0}, {0,0}, {0,0}, {0,0} }).toString());
        assertEquals(history.get(2), new Rope(
                new int[][] {{1, 4}, {2,4}, {3,3}, {3,2}, {2,2}, {1,1}, {0,0}, {0,0}, {0,0}, {0,0} }).toString());
        assertEquals(history.get(3), new Rope(
                new int[][] {{1, 3}, {2,4}, {3,3}, {3,2}, {2,2}, {1,1}, {0,0}, {0,0}, {0,0}, {0,0} }).toString());
        assertEquals(history.get(4), new Rope(
                new int[][] {{5,3}, {4,3}, {3,3}, {3,2}, {2,2}, {1,1}, {0,0}, {0,0}, {0,0}, {0,0} }).toString());
        assertEquals(history.get(5), new Rope(
                new int[][] {{5,2}, {4,3}, {3,3}, {3,2}, {2,2}, {1,1}, {0,0}, {0,0}, {0,0}, {0,0} }).toString());
        assertEquals(history.get(6), new Rope(
                new int[][] {{0,2}, {1,2}, {2,2}, {3,2}, {2,2}, {1,1}, {0,0}, {0,0}, {0,0}, {0,0} }).toString());
        assertEquals(history.get(7), new Rope(
                new int[][] {{2,2}, {1,2}, {2,2}, {3,2}, {2,2}, {1,1}, {0,0}, {0,0}, {0,0}, {0,0} }).toString());
    }

    @Test
    public void testProcessStepsForRope() {
        assertEquals(
                new RopeTailTracer().processStepsForRope(
                        List.of("R 5", "U 8", "L 8", "D 3", "R 17", "D 10", "L 25", "U 20")),
                36);
    }

}