package src.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class FileReader {

    public static final String SEPARATOR_EMPTY_LINE = "";

    public static List<String> readFileToLines(String filename) throws IOException {
        return Files.readAllLines(Paths.get(filename));
    }

    public static String getInputFilename(String filePathFormat, int dayOfAdvent) {
        String day = dayOfAdvent < 10 ? "0" + dayOfAdvent : String.valueOf(dayOfAdvent);
        return String.format(filePathFormat, day);
    }

    /** splits a list horizontally and separates entries into sublist */
    public static List<List<String>> splitListBySeparator(List<String> input, String separator) {
        int[] indexes = Stream.of(
                IntStream.of(-1), IntStream.range(0, input.size()).filter(i -> input.get(i).equals(separator)),
                IntStream.of(input.size())).flatMapToInt(s -> s).toArray();
        return IntStream.range(0, indexes.length-1)
                .mapToObj(i -> input.subList(indexes[i]+1, indexes[i+1])).collect(toList());
    }

    public static List<Character> convertStringToCharList(String s) {
        return s.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }
}
