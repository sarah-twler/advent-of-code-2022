package src.dayofadvent;

import src.utils.FileReader;

import java.io.IOException;
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
        return calorieSplitLists.stream()
                .map(c -> sumCalories(c))
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static List<List<String>> getCaloriesListPerElf(String calorieInputFilename) throws IOException {
        List<String> calorieInputList = FileReader.readFileToLines(calorieInputFilename);
        return FileReader.splitListBySeparator(calorieInputList, "");
    }

    private static int sumCalories(List<String> calorieList) {
        return calorieList.stream().mapToInt(Integer::valueOf).sum();
    }
}
