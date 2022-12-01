package src.dayofadvent;

import src.utils.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CalorieCounter {

    public static int calcMaxCaloriesCarried(String calorieInputFilename) throws IOException {
        List<List<String>> calorieSplitLists = getCaloriesListPerElf(calorieInputFilename);

        int maxCalories = 0;

        for (List<String> items : calorieSplitLists) {
            int calories = sumCalories(items);
            if (calories > maxCalories) {
                maxCalories = calories;
            }
        }
        return maxCalories;
    }

    public static int calcCaloriesCarriedByTopThree(String calorieInputFilename) throws IOException {
        List<List<String>> calorieSplitLists = getCaloriesListPerElf(calorieInputFilename);

        List<Integer> calories = new ArrayList();
        for (List<String> items : calorieSplitLists) {
            calories.add(sumCalories(items));
        }

        return calories.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(Integer::intValue).sum();
    }

    private static List<List<String>> getCaloriesListPerElf(String calorieInputFilename) throws IOException {
        List<String> calorieInputList = FileReader.readFileToLines(calorieInputFilename);
        List<List<String>> calorieSplitLists = FileReader.splitListBySeparator(calorieInputList, "");
        return calorieSplitLists;
    }

    private static int sumCalories(List<String> calorieList) {
        int calories = 0;
        for (String calorie : calorieList) {
            calories += Integer.valueOf(calorie);
        }
        return calories;
    }
}
