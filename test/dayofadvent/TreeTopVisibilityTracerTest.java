package test.dayofadvent;


import org.testng.annotations.Test;
import src.dayofadvent.TreeTopVisibilityTracer;
import src.utils.FileReader;

import java.io.IOException;
import java.util.Arrays;

import static org.testng.Assert.*;
import static test.dayofadvent.TestConstant.FILE_PATH_FORMAT;

public class TreeTopVisibilityTracerTest {

    static private final int DAY_OF_ADVENT = 8;
    static private final int[][] expected = {
            {3,0,3,7,3},
            {2,5,5,1,2},
            {6,5,3,3,2},
            {3,3,5,4,9},
            {3,5,3,9,0}
    };

    @Test
    public void testBuildTreeMatrix() throws IOException {
        int[][] result = TreeTopVisibilityTracer.buildTreeMatrix(
                FileReader.readFileToLines(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)));

        assertEquals(result.length, 5);
        assertTrue(Arrays.equals(expected[0], result[0]));
        assertTrue(Arrays.equals(expected[1], result[1]));
        assertTrue(Arrays.equals(expected[2], result[2]));
        assertTrue(Arrays.equals(expected[3], result[3]));
        assertTrue(Arrays.equals(expected[4], result[4]));
    }

    @Test
    public void testGetNumberVisibleEdge() {
        assertEquals(TreeTopVisibilityTracer.getNumberVisibleEdge(expected), 16);
    }

    @Test
    public void testGetNumberVisibleInterior() {
        assertEquals(5, TreeTopVisibilityTracer.getNumberVisibleInterior(expected));
    }

    @Test
    public void testIsVisibleHorizontal() {
        assertTrue(TreeTopVisibilityTracer.isVisible(expected, 1, 1));
        assertTrue(TreeTopVisibilityTracer.isVisible(expected, 1, 2));
        assertFalse(TreeTopVisibilityTracer.isVisible(expected, 1, 3));
        assertTrue(TreeTopVisibilityTracer.isVisible(expected, 2, 1));
        assertFalse(TreeTopVisibilityTracer.isVisible(expected, 2, 2));
        assertTrue(TreeTopVisibilityTracer.isVisible(expected, 2, 3));
        assertFalse(TreeTopVisibilityTracer.isVisible(expected, 3, 1));
        assertTrue(TreeTopVisibilityTracer.isVisible(expected, 3, 2));
        assertFalse(TreeTopVisibilityTracer.isVisible(expected, 3, 3));
    }

    @Test
    public void testFindVisibleTrees() {
        assertEquals(21, TreeTopVisibilityTracer.findVisibleTrees(expected));
    }

    @Test
    public void testGetScenicScore() {
        assertEquals(TreeTopVisibilityTracer.getScenicScore(expected, 1, 2), 4);
        assertEquals(TreeTopVisibilityTracer.getScenicScore(expected, 3, 2), 8);
    }

    @Test
    public void testGetHighestScenicScore() {
        assertEquals(8, TreeTopVisibilityTracer.getHighestScenicScore(expected));
    }

}