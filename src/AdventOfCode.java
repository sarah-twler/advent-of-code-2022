package src;

import src.utils.FileReader;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdventOfCode {

    static private final String FILE_PATH_FORMAT = "./data/advent/%s_input.txt";
    private static final String[] TITLE = {"first", "second"};

    public static void main(String[] args) throws IOException {
        int dayOfAdvent = getDayOfAdvent();

        /**
         * process here
         */
        switch (dayOfAdvent) {
            case 1:
                System.out.println("Call result calculation here");
                break;
            default:
                System.out.println(String.format("Code for day %s not implemented yet. Try again later!", dayOfAdvent));
                return;
        }

        // test reading input file
        List<String> lines = FileReader.readFileToLines(getInputFilename(dayOfAdvent));
        for (String line : lines) {
            System.out.println(line);
        }

        System.out.println(String.format("Day %s - %s", dayOfAdvent, TITLE[dayOfAdvent - 1]));
        var result = "";
        System.out.println("Result: " + result);
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

    private static String getInputFilename(int dayOfAdvent) {
        String day = dayOfAdvent < 10 ? "0" + dayOfAdvent : String.valueOf(dayOfAdvent);
        return String.format(FILE_PATH_FORMAT, day);
    }
}
