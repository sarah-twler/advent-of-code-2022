package test.dayofadvent;


import org.testng.annotations.Test;
import src.dayofadvent.DirSizeCalculator;
import src.utils.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static test.dayofadvent.TestConstant.FILE_PATH_FORMAT;

public class DirSizeCalculatorTest {

    static private final int DAY_OF_ADVENT = 7;

    @Test
    public void testGetTotalFileSize() {
        List<String> input = Arrays.asList("dir a", "14848514 b.txt", "8504156 c.dat", "dir d");
        assertEquals(14848514 + 8504156, DirSizeCalculator.getTotalFileSize(input));
    }

    @Test
    public void testGetFileSize() {
        assertEquals(DirSizeCalculator.getFileSize("8504156 c.dat"), 8504156);
        assertEquals(DirSizeCalculator.getFileSize("247491 nrsrmplp"), 247491);
        assertEquals(DirSizeCalculator.getFileSize("dir a"), 0);
    }

    @Test
    public void testGetDirName() {
        assertEquals(DirSizeCalculator.getDirName("dir a"), "a");
        assertEquals(DirSizeCalculator.getDirName("dir mnm"), "mnm");
        assertEquals(DirSizeCalculator.getDirName("247491 nrsrmplp"), null);
    }

    @Test
    public void testGetChangeToDirName() {
        assertEquals(DirSizeCalculator.getChangeToDirName("$ cd a"), "a");
        assertEquals(DirSizeCalculator.getChangeToDirName("$ cd rtpjd"), "rtpjd");
        assertEquals(DirSizeCalculator.getChangeToDirName("dir a"), null);
    }

    @Test
    public void testGetSubDirList() {
        List<String> input = Arrays.asList("dir a", "14848514 b.txt", "8504156 c.dat", "dir d");
        assertEquals(DirSizeCalculator.getSubDirs(input), List.of("a", "d"));
    }

    @Test
    public void testGetSumMaxSizeDirs() throws IOException {
        assertEquals(
                DirSizeCalculator.getSumMaxSizeDirs(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                95437);
    }

    @Test
    public void testCalcDeletionSize() throws IOException {
        assertEquals(DirSizeCalculator.calcDeletionSize(
                FileReader.readFileToLines(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT))),
                8381165);
    }

    @Test
    public void testCalcSizeOfDeletionDir() throws IOException {
        assertEquals(
                DirSizeCalculator.calcSizeOfDeletionDir(FileReader.getInputFilename(FILE_PATH_FORMAT, DAY_OF_ADVENT)),
                24933642);
    }

}