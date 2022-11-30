package src;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdventOfCode {

    static private final String FILE_PATH_FORMAT = "./data/advent/%s_input";
    private static final String[] TITLE = {"first", "second"};

    public static void main(String[] args) {
        int dayOfAdvent = getDayOfAdvent();

        if (dayOfAdvent > TITLE.length) {
            System.out.println(String.format("Code for day %s not implemented yet. Try again later!", dayOfAdvent));
            return;
        }
        
        /**
         * process here
         */

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

    private String getInputFilename(int dayOfAdvent) {
        String day = dayOfAdvent < 10 ? "0" + dayOfAdvent : String.valueOf(dayOfAdvent);
        return String.format(FILE_PATH_FORMAT, day);
    }
}
