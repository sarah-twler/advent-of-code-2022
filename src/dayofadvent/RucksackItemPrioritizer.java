package src.dayofadvent;

import org.testng.internal.collections.Pair;
import src.utils.FileReader;

import java.io.IOException;
import java.util.*;

import static src.utils.FileReader.convertStringToCharList;

public class RucksackItemPrioritizer {

    public static int getItemPrioritySum(String contentListFilename) throws IOException {
        List<String> contentLists = FileReader.readFileToLines(contentListFilename);
        return contentLists.stream()
                .map(c -> splitContentList(c))
                .map(i -> getMutualItems(i.first(), i.second()))
                .mapToInt(p -> sumPriorities(p))
                .sum();
    }

    public static int getGroupPrioritySum(String contentListFilename) throws IOException {
        List<String> contentLists = FileReader.readFileToLines(contentListFilename);

        int sum = 0;
        for (int index = 0; index < contentLists.size(); index += 3)
            sum += sumPriorities(
                    getGroupBadges(
                            contentLists.get(index),
                            contentLists.get(index + 1),
                            contentLists.get(index + 2)));

        return sum;
    }

    private static Set<Character> getGroupBadges(String content1, String content2, String content3) {
        return getMutualItems(
                  getMutualItems(
                        convertStringToCharList(content1),
                        convertStringToCharList(content2)),
                convertStringToCharList(content3));
    }

    private static int sumPriorities(Set<Character> items) {
        return items.stream().map(p -> getPriority(p)).mapToInt(Integer::intValue).sum();
    }

    private static Set<Character> getMutualItems(Collection<Character> comp1, Collection<Character> comp2){
        Set<Character> mutualItems = new HashSet(comp1);
        mutualItems.retainAll(comp2);
        return mutualItems;
    }

//    Lowercase item types a through z have priorities 1 through 26.
//    Uppercase item types A through Z have priorities 27 through 52.
    private static int getPriority(Character item){
        if (!Character.isLetter(item))
            return -1;
        return Character.isLowerCase(item) ?
                Character.getNumericValue(item) - 9 :
                Character.getNumericValue(item) + 17;
    }

    private static Pair<List<Character>, List<Character>> splitContentList(String contentList) {
        List<Character> l = convertStringToCharList(contentList);
        int splitIndex = l.size() / 2;
        return new Pair<>(l.subList(0, splitIndex), l.subList(splitIndex, l.size()));
    }

}
