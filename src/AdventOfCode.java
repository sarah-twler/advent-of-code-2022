package src;

import src.dayofadvent.*;
import src.utils.FileReader;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdventOfCode {

    static private final String FILE_PATH_FORMAT = "./data/advent/%s_data.txt";
    private static final String[] TITLE =
            {"Calorie Counting", "Rock Paper Scissors", "Rucksack Reorganization", "Camp Cleanup", "Supply Stacks",
                    "Tuning Trouble", "No Space Left On Device", "Treetop Tree House", "Rope Bridge", "Cathode-Ray Tube",
                    "Monkey in the Middle"};

    public static void main(String[] args) throws IOException, IllegalAccessException {
        int dayOfAdvent = getDayOfAdvent();

        String result1;
        String result2;
        String inputFilename = FileReader.getInputFilename(FILE_PATH_FORMAT, dayOfAdvent);
        switch (dayOfAdvent) {
            case 1:
                result1 = String.valueOf(CalorieCounter.calcMaxCaloriesCarried(inputFilename));
                result2 = String.valueOf(CalorieCounter.calcCaloriesCarriedByTopThree(inputFilename));
                break;
            case 2:
                result1 = String.valueOf(StrategyGuideEvaluator.calcScoreByPlayerAction(inputFilename));
                result2 = String.valueOf(StrategyGuideEvaluator.calcScoreByPlayerOutcome(inputFilename));
                break;
            case 3:
                result1 = String.valueOf(RucksackItemPrioritizer.getItemPrioritySum(inputFilename));
                result2 = String.valueOf(RucksackItemPrioritizer.getGroupPrioritySum(inputFilename));
                break;
            case 4:
                result1 = String.valueOf(CleanupPairOrganizer.getSumOfPairsFullyContained(inputFilename));
                result2 = String.valueOf(CleanupPairOrganizer.getSumOfPairsOverlapped(inputFilename));
                break;
            case 5:
                result1 = CargoCrane.getStackTopsCraneType9000(inputFilename);
                result2 = CargoCrane.getStackTopsCraneType9001(inputFilename);;
                break;
            case 6:
                result1 = String.valueOf(SignalProcessor.getNumberOfCharactersBeforeSignal(inputFilename));
                result2 = String.valueOf(SignalProcessor.getNumberOfCharactersBeforeMessage(inputFilename));
                break;
            case 7:
                result1 = String.valueOf(DirSizeCalculator.getSumMaxSizeDirs(inputFilename));
                result2 = String.valueOf(DirSizeCalculator.calcSizeOfDeletionDir(inputFilename));
                break;
            case 8:
                result1 = String.valueOf(TreeTopVisibilityTracer.getNumberVisibleTrees(inputFilename));
                result2 = String.valueOf(TreeTopVisibilityTracer.calcBestScenicScore(inputFilename));
                break;
            case 9:
                result1 = String.valueOf(new RopeTailTracer().countTailPositions(inputFilename));
                result2 = String.valueOf(new RopeTailTracer().countTailPositionsRope(inputFilename));
                break;
            case 10:
                result1 = String.valueOf(SignalStrengthCalculator.getSumSignalStrength(inputFilename));
                result2 = System.lineSeparator() + SignalStrengthCalculator.getCrtScreen(inputFilename);
                break;
            case 11:
                result1 = String.valueOf(MonkeyPredictor.getMonkeyBusinessLevelLow(inputFilename));
                result2 = "tbd";
                break;
            default:
                System.out.println(String.format("Code for day %s not implemented yet. Try again later!", dayOfAdvent));
                return;
        }

        System.out.println(String.format("Day %s - %s", dayOfAdvent, TITLE[dayOfAdvent - 1]));
        System.out.println(String.format("Result 1: %s\nResult 2: %s", result1, result2));
    }

    private static int getDayOfAdvent() {
        int dayOfAdvent = 0;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Welcome to Advent of Code!\nEnter a day (1-24): ");
            dayOfAdvent = scanner.nextInt();
            scanner.close();
            return dayOfAdvent;
        } catch (InputMismatchException e) {
            // do nothing
        }

        if (dayOfAdvent < 1 || dayOfAdvent > 24) {
            System.out.println("Invalid day. Try again!");
            System.exit(0);
        }
        return dayOfAdvent;
    }

}
