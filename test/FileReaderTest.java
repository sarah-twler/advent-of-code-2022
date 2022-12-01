package test;

import org.testng.annotations.Test;
import src.utils.FileReader;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FileReaderTest {

    @Test
    public void testSplitListBySeparator() {
        List<String> list = new ArrayList();
        list.add("1000");
        list.add("2000");
        list.add("3000");
        list.add("");
        list.add("4000");
        list.add("");
        list.add("5000");
        list.add("6000");
        list.add("");
        list.add("7000");
        list.add("8000");
        list.add("9000");
        list.add("");
        list.add("10000");

        List<List<String>> result = FileReader.splitListBySeparator(list, "");
        assertEquals(result.size(), 5);
        assertTrue(result.get(0).containsAll(list.subList(0, 3)));
        assertTrue(result.get(1).containsAll(list.subList(4, 5)));
        assertTrue(result.get(2).containsAll(list.subList(6, 8)));
        assertTrue(result.get(3).containsAll(list.subList(9, 12)));
        assertTrue(result.get(4).containsAll(list.subList(13, 14)));
    }
}
