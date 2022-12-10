package src.dayofadvent;

import src.utils.FileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DirSizeCalculator {

    private static final int MAX_SIZE = 100000;
    private static final int DISK_SPACE = 70000000;
    private static final int UPDATE_SIZE = 30000000;
    private static final String COMMAND_INDICATOR = "$";
    private static final String COMMAND_DIR_UP = "$ cd ..";
    private static final String COMMAND_LIST = "$ ls";

    private static final Pattern patternFileSize = Pattern.compile("(\\d+)\\s\\w+.?\\w*");
    private static final Pattern patternGetDirName = Pattern.compile("dir\\s(\\w+)");
    private static final Pattern patternChangeToDir = Pattern.compile("\\$\\scd\\s(\\w+|\\/)");

    private static int totalSum = 0;
    private static int pointer = 0;
    private static int deletionSize = 0;
    private static boolean enableDeletionCalc = false;
    private static List<Integer> deletionCandidates = new ArrayList();

    public static int getSumMaxSizeDirs(String filename) throws IOException {
        List<String> lines = FileReader.readFileToLines(filename);
        ArrayList<String> starter = new ArrayList();
        starter.add("/");
        pointer = 0;
        enableDeletionCalc = false;
        nextSteps(lines, starter);
        return totalSum;
    }

    private static int nextSteps(List<String> lines, ArrayList<String> sisterDirs) {
        if (pointer >= lines.size() && !sisterDirs.isEmpty())
            throw new IllegalArgumentException("Unexpected state! EOF, but sisterDirs not visited!");
        if (pointer >= lines.size() || COMMAND_DIR_UP.equals(lines.get(pointer))) {
            pointer++;
            return 0;
        }

        String currDir = getChangeToDirName(lines.get(pointer));
        if (currDir == null)
            throw new IllegalArgumentException("Unexpected command line! Expected '$ cd dir', but was " + lines.get(pointer));
        pointer++;
        if (!lines.get(pointer).equals(COMMAND_LIST))
            throw new IllegalArgumentException("Unexpected command after change dir! Expected '$ ls', but was " + lines.get(pointer));

        sisterDirs.remove(currDir);
        pointer++;
        List<String> listedContent = lsDir(lines, pointer);
        int dirSize = getTotalFileSize(listedContent);
        ArrayList<String> subDirs = getSubDirs(listedContent);
        pointer += listedContent.size();
        while (!subDirs.isEmpty())
            dirSize += nextSteps(lines, subDirs);

        if (dirSize < MAX_SIZE)
            totalSum += dirSize;

        if (enableDeletionCalc && dirSize > deletionSize)
            deletionCandidates.add(dirSize);

        return dirSize;
    }

    public static int calcSizeOfDeletionDir(String filename) throws IOException {
        List<String> lines = FileReader.readFileToLines(filename);
        deletionSize = calcDeletionSize(lines);
        enableDeletionCalc = true;
        ArrayList<String> starter = new ArrayList();
        starter.add("/");
        pointer = 0;
        nextSteps(lines, starter);
        return Collections.min(deletionCandidates);
    }

    public static int calcDeletionSize(List<String> lines) {
        ArrayList<String> starter = new ArrayList();
        starter.add("/");
        pointer = 0;
        int usedSpace = nextSteps(lines, starter);
        int freeSpace = DISK_SPACE - usedSpace;
        return UPDATE_SIZE - freeSpace;
    }

    public static int getTotalFileSize(List<String> listedContent) {
        return listedContent.stream().map(DirSizeCalculator::getFileSize).mapToInt(Integer::intValue).sum();
    }

    public static int getFileSize(String line) {
        Matcher m = patternFileSize.matcher(line);
        if (m.find())
            return Integer.valueOf(m.group(1));
        return 0;
    }

    public static ArrayList<String> getSubDirs(List<String> listedContent) {
        return listedContent.stream()
                .filter(l -> getDirName(l) != null)
                .map(DirSizeCalculator::getDirName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static String getDirName(String line) {
        Matcher m = patternGetDirName.matcher(line);
        if (m.find())
            return m.group(1);
        return null;
    }

    public static String getChangeToDirName(String line) {
        Matcher m = patternChangeToDir.matcher(line);
        if (m.find())
            return m.group(1);
        return null;
    }

    private static List<String> lsDir(List<String> line, int index) {
        List<String> lsList = new ArrayList();
        while (index < line.size() && !line.get(index).startsWith(COMMAND_INDICATOR)) {
            lsList.add(line.get(index));
            index++;
        }
        return lsList;
    }

}
