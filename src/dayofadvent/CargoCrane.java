package src.dayofadvent;

import src.utils.FileReader;

import java.io.IOException;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CargoCrane {

    private static final int WIDTH_OF_CRATE_SLOT = 4;
    private static final int POSITION_OF_CRATE_IN_SLOT = 1;
    private static final Character EMPTY_SLOT = ' ';
    private static Pattern digitPattern = Pattern.compile("\\d+");
    private enum CraneType{
        TYPE_9000,
        TYPE_9001
    }

    public static String getStackTopsCraneType9000(String setupFilename) throws IOException {
        return getStackTops(setupFilename, CraneType.TYPE_9000);
    }

    public static String getStackTopsCraneType9001(String setupFilename) throws IOException {
        return getStackTops(setupFilename, CraneType.TYPE_9001);
    }

    private static String getStackTops(String setupFilename, CraneType craneType) throws IOException {
        Map<Integer, LinkedList<Character>> stacks = getInitialStacks(setupFilename);
        List<String> proceduresList = getProcedures(setupFilename);
        moveCrates(stacks, proceduresList, craneType);
        List<Character> topCratesChars = getTopCrates(stacks);
        return topCratesChars.stream().map(c -> Character.toString(c)).collect(Collectors.joining(""));
    }

    private static List<Character> getTopCrates(Map<Integer, LinkedList<Character>> stacks) {
        return IntStream
                .range(Collections.min(stacks.keySet()), Collections.max(stacks.keySet()) + 1)
                .map(k -> stacks.get(k).getLast())
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
    }

    private static Map<Integer, LinkedList<Character>> getInitialStacks(String setupFilename) throws IOException {
        ArrayList<String> stackSetup = new ArrayList(readSetup(setupFilename).get(0));
        return initStacks(stackSetup);
    }

    private static List<String> getProcedures(String setupFilename) throws IOException {
        return readSetup(setupFilename).get(1);
    }

    private static List<List<String>> readSetup(String setupFilename) throws IOException {
        List<String> setup = FileReader.readFileToLines(setupFilename);
        return FileReader.splitListBySeparator(setup, FileReader.SEPARATOR_EMPTY_LINE);
    }

    private static void moveCrates(Map<Integer, LinkedList<Character>> mapOfStacks, List<String> procedureList, CraneType craneType) {
        procedureList.forEach(p -> moveCrates(mapOfStacks, getCraneAction(p), craneType));
    }

    private static void moveCrates(Map<Integer, LinkedList<Character>> crateStacks, CraneAction procedure,
                                   CraneType craneType) {
        int maxIndex = crateStacks.get(procedure.posFrom).size();
        List<Character> pickedCrates = crateStacks.get(procedure.posFrom).subList(maxIndex - procedure.amount, maxIndex);
        if (CraneType.TYPE_9000.equals(craneType))
            Collections.reverse(pickedCrates);
        crateStacks.get(procedure.posTo).addAll(pickedCrates);
        pickedCrates.clear();
    }

    public static CraneAction getCraneAction(String instruction) {
        Matcher m = digitPattern.matcher(instruction);
        List<Integer> digits = m.results().map(MatchResult::group).map(Integer::parseInt).collect(Collectors.toList());
        return new CraneAction(digits.get(0), digits.get(1), digits.get(2));
    }


    public static List<Character> getListOfCrates(String horizontalLineOfCrates) {
        return Pattern.compile(".{1," + WIDTH_OF_CRATE_SLOT + "}")
                .matcher(horizontalLineOfCrates)
                .results()
                .map(MatchResult::group)
                .map(c -> c.charAt(POSITION_OF_CRATE_IN_SLOT))
                .collect(Collectors.toList());
    }

    public static Map<Integer, LinkedList<Character>> initStacks(ArrayList<String> stacksSetup) {
        Collections.reverse(stacksSetup);
        String indexLine = stacksSetup.get(0);
        Map<Integer, LinkedList<Character>> stacks = initEmptyMap(getNoOfStacks(indexLine));
        stacksSetup.remove(0);
        stacksSetup.forEach(s -> putCratesInMap(s, stacks));
        return stacks;
    }

    private static void putCratesInMap(String horizontalLineOfCrates, Map<Integer, LinkedList<Character>> map) {
        List<Character> crates = getListOfCrates(horizontalLineOfCrates);
        for (int i = 1; i <= crates.size(); i++) {
            if (!EMPTY_SLOT.equals(crates.get(i - 1)))
                map.get(i).add(crates.get(i - 1));
        }
    }

    private static int getNoOfStacks(String stackIndexLine) {
        String indexes = stackIndexLine.replaceAll(" ", "");
        return Character.getNumericValue(indexes.charAt(indexes.length() - 1));
    }

    private static Map<Integer, LinkedList<Character>> initEmptyMap(int noOfStacks) {
        Map<Integer, LinkedList<Character>> map = new HashMap();
        IntStream.range(1, noOfStacks + 1).forEach(i -> map.put(i, new LinkedList()));
        return map;
    }

    public static class CraneAction {

        private int amount;
        private int posFrom;
        private int posTo;

        public CraneAction(Integer amount, Integer posFrom, Integer posTo) {
            this.amount = amount;
            this.posFrom = posFrom;
            this.posTo = posTo;
        }

        public int[] getInstruction() {
            return new int[]{amount, posFrom, posTo};
        }

    }
}
