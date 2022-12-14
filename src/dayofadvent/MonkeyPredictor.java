package src.dayofadvent;

import org.testng.internal.collections.Pair;
import src.utils.FileReader;

import java.io.IOException;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MonkeyPredictor {

    public static int getMonkeyBusinessLevelLow(String monkeyAttributesFile) throws IOException {
        return doMonkeyBusiness(20, true, monkeyAttributesFile);
    }

    public static int getMonkeyBusinessLevelHigh(String monkeyAttributesFile) throws IOException {
        return doMonkeyBusiness(10000, false, monkeyAttributesFile);
    }

    private static int doMonkeyBusiness(int timer, boolean includeRelief, String monkeyAttributesFile) throws IOException {
        List<String> monkeyAttributeLines = FileReader.readFileToLines(monkeyAttributesFile);
        List<List<String>> monkeyAttributes = FileReader.splitListBySeparator(monkeyAttributeLines, FileReader.SEPARATOR_EMPTY_LINE);

        LinkedList<Monkey> monkeys = monkeyAttributes
                .stream()
                .map(m -> createMonkey(m, includeRelief))
                .collect(Collectors.toCollection(LinkedList::new));

//        int round = 0;
//        while (round < timer) {
//            doMonkeyBusiness(monkeys);
//            printMonkeys(round, monkeys);
//            round++;
//        }
        IntStream.range(0, timer).forEach(i -> doMonkeyBusiness(monkeys));

        List<Integer> activityLevels = monkeys.stream().map(Monkey::getActivityLevel).collect(Collectors.toList());

        Collections.sort(activityLevels);
        Collections.reverse(activityLevels);

//        return new BigInteger(String.valueOf(activityLevels.get(0)))
//                .multiply(new BigInteger(String.valueOf(activityLevels.get(1))));
        return activityLevels.get(0) * activityLevels.get(1);
    }

    private static void doMonkeyBusiness(LinkedList<Monkey> monkeys) {
        for (int m = 0; m < monkeys.size(); m++) {
            throwItems(monkeys, monkeys.get(m).monkeysTurn());
        }
    }

    private static void printMonkeys(int round, LinkedList<Monkey> monkeys) {
        System.out.println("Monkeys after round " + round);
        for (Monkey monkey : monkeys)
            System.out.println(String.format("Monkey %s: %s", monkey.monkeyId, monkey.items));
    }

    private static void throwItems(LinkedList<Monkey> monkeys, LinkedList<Pair<Integer, Integer>> itemAndMonkeyList) {
        itemAndMonkeyList.stream().forEach(l -> monkeys.get(l.second()).catchItem(l.first()));
    }

    private static Monkey createMonkey(List<String> monkeyAttributes, boolean includeRelief) {
        if (monkeyAttributes.size() != 6)
            throw new IllegalArgumentException("Lines with monkey attributes has unexpected format! " + monkeyAttributes);
        int monkeyId = getDigitByRegex(monkeyAttributes.get(0));
        int[] items = getDigitsByRegex(monkeyAttributes.get(1));
        int operationValue = -1;
        try {
            operationValue = getDigitByRegex(monkeyAttributes.get(2));
        } catch (NoSuchElementException e) {}
        MonkeyOperation monkeyOperation = getOperationByRegex(monkeyAttributes.get(2));
        int divider = getDigitByRegex(monkeyAttributes.get(3));
        int nextMonkeyTrue = getDigitByRegex(monkeyAttributes.get(4));
        int nextMonkeyFalse = getDigitByRegex(monkeyAttributes.get(5));
        return new Monkey(monkeyId, monkeyOperation, operationValue, divider, nextMonkeyTrue, nextMonkeyFalse, items, includeRelief);
    }

    private static int getDigitByRegex(String attribute) {
        return getIntStreamForDigitByRegex(attribute).findFirst().getAsInt();
    }

    private static int[] getDigitsByRegex(String attribute) {
        return getIntStreamForDigitByRegex(attribute).toArray();
    }

    private static IntStream getIntStreamForDigitByRegex(String attribute) {
        return Pattern.compile("(\\d+)")
                .matcher(attribute)
                .results()
                .map(MatchResult::group)
                .mapToInt(Integer::valueOf);
    }

    private static MonkeyOperation getOperationByRegex(String attribute) {
        return MonkeyOperation.valueOfOperation(
                Pattern.compile("(\\*|\\+)")
                        .matcher(attribute)
                        .results()
                        .map(MatchResult::group)
                        .findFirst()
                        .get());
    }


    public enum MonkeyOperation {
        ADD,
        MULTIPLY,
        SQUARE,
        UNKNOWN;

        public static MonkeyOperation valueOfOperation(String monkeyOperation) {
            switch (monkeyOperation) {
                case "+": return ADD;
                case "*": return MULTIPLY;
                default: return UNKNOWN;
            }
        }
    }

    static class Monkey {

        private final int monkeyId;
        private LinkedList<Integer> items;
        private MonkeyOperation monkeyOperation;
        private int operationValue;
        private int divider;
        private int nextMonkeyIdTrue;
        private int nextMonkeyIdFalse;
        private int countInspections;
        private boolean includeRelief;


        public Monkey(int monkeyId, MonkeyOperation monkeyOperation, int operationValue, int divider, int nextMonkeyIdTrue, int nextMonkeyIdFalse, int[] items, boolean includeRelief) {
            this.monkeyId = monkeyId;

            this.monkeyOperation = operationValue < 0 ? MonkeyOperation.SQUARE : monkeyOperation;
            this.operationValue = operationValue;

            this.divider = divider;
            this.nextMonkeyIdTrue = nextMonkeyIdTrue;
            this.nextMonkeyIdFalse = nextMonkeyIdFalse;

            this.items = Arrays.stream(items).boxed().collect(Collectors.toCollection(LinkedList::new));
            this.countInspections = 0;
            this.includeRelief = includeRelief;
        }

        public void catchItem(int item) {
            items.add(item);
        }

        /** pair<worryLevel, throwToMonkey> **/
        public LinkedList<Pair<Integer, Integer>> monkeysTurn() {
            LinkedList<Pair<Integer, Integer>> throwables =
                    items.stream().map(i -> inspectItem(i)).collect(Collectors.toCollection(LinkedList::new));
            items.clear();
            return throwables;
        }

        /** pair<worryLevel, throwToMonkey> **/
        private Pair<Integer, Integer> inspectItem(int item) {
            countInspections++;
            int worryLevel = operateItem(item);
            return new Pair<>(worryLevel, testItem(worryLevel));
        }

        private int getActivityLevel() {
            return countInspections;
        }

        private int testItem(int item) {
            return item % divider == 0 ? nextMonkeyIdTrue : nextMonkeyIdFalse;

        }

        private int operateItem(int currWorryLevel) {
            switch (monkeyOperation) {
                case ADD: return includeReliefLevel(currWorryLevel + operationValue);
                case MULTIPLY: return includeReliefLevel(currWorryLevel * operationValue);
                case SQUARE: return includeReliefLevel(currWorryLevel * currWorryLevel);
            }
            throw new IllegalArgumentException("Got unknown monkey operation!");
        }

        private int includeReliefLevel(int operationResult) {
            return includeRelief ? operationResult / 3 : operationResult;
        }
    }
}
