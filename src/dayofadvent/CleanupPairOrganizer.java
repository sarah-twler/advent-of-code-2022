package src.dayofadvent;

import org.testng.internal.collections.Pair;
import src.utils.FileReader;

import java.io.IOException;
import java.util.List;

public class CleanupPairOrganizer {

    private static final String PAIR_SEPARATOR = ",";
    private static final String RANGE_SEPARATOR = "-";

    public static int getSumOfPairsFullyContained(String pairsFilename) throws IOException {
        List<String> listOfPairs = FileReader.readFileToLines(pairsFilename);
        return (int) listOfPairs.stream()
                .map(x -> getRangeStartEndForPair(x, PAIR_SEPARATOR, RANGE_SEPARATOR))
                .filter(s -> isSectionContained(s.first(), s.second()))
                .count();
    }

    public static int getSumOfPairsOverlapped(String pairsFilename) throws IOException {
        List<String> listOfPairs = FileReader.readFileToLines(pairsFilename);
        return (int) listOfPairs.stream()
                .map(x -> getRangeStartEndForPair(x, PAIR_SEPARATOR, RANGE_SEPARATOR))
                .filter(s -> isSectionOverlapped(s.first(), s.second()))
                .count();
    }

    private static Pair<int[], int[]> getRangeStartEndForPair(String entry, String pairSeparator, String rangeSeparator) {
        String[] sections = entry.split(pairSeparator);
        return new Pair(
                separateSectionStartEnd(sections[0], rangeSeparator),
                separateSectionStartEnd(sections[1], rangeSeparator));
    }

    private static int[] separateSectionStartEnd(String section, String separator) {
        String[] sectionStartEnd = section.split(separator);
        String sectionStart = sectionStartEnd[0];
        String sectionEnd = sectionStartEnd[1];
        return new int[]{Integer.valueOf(sectionStart), Integer.valueOf(sectionEnd)};
    }

    private static boolean isSectionContained(int[] section1, int[] section2) {
        int start = Integer.compare(section1[0], section2[0]);
        int end = Integer.compare(section1[1], section2[1]);
        return !(start == end && start != 0 && end != 0);
        // is not contained if comparison of starts and ends have same results,
        // unless one or both of the results is 0, i.e. edges match
    }

    private static boolean isSectionOverlapped(int[] section1, int[] section2) {
        return !(section1[1] < section2[0] || section2[1] < section1[0]);
        // no overlap if start of section 1 is after end of section 2
        // or start of section 2 is after end of section 1
    }

}
