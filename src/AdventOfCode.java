package src;

import src.dayofadvent.CalorieCounter;
import src.utils.FileReader;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdventOfCode {

    static private final String FILE_PATH_FORMAT = "./data/advent/%s_data.txt";
    private static final String[] TITLE = {"Calorie Counting"};

    public static void main(String[] args) throws IOException {
        int dayOfAdvent = getDayOfAdvent();

        int result1;
        int result2;
        String inputFilename = FileReader.getInputFilename(FILE_PATH_FORMAT, dayOfAdvent);
        switch (dayOfAdvent) {
            case 1:
                result1 = CalorieCounter.calcMaxCaloriesCarried(inputFilename);
                result2 = CalorieCounter.calcCaloriesCarriedByTopThree(inputFilename);
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
