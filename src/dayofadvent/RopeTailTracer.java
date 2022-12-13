package src.dayofadvent;

import src.utils.FileReader;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RopeTailTracer {

    private static final Pattern regexPattern = Pattern.compile("(\\w)\\s(\\d+)");

    private int[] posHead = new int[]{0,0};
    private int[] posTail = new int[]{0,0};
    private Rope rope = new Rope();
    private Set<String> tailHistory = new HashSet();
    private LinkedList<String> ropeHistory = new LinkedList();

    public int countTailPositions(String headStepsFilename) throws IOException {
        List<String> headSteps = FileReader.readFileToLines(headStepsFilename);
        return processSteps(headSteps);
    }

    public int countTailPositionsRope(String headStepsFilename) throws IOException {
        List<String> headSteps = FileReader.readFileToLines(headStepsFilename);
        return processStepsForRope(headSteps);
    }

    public int processSteps(List<String> headSteps) {
        addPosTailToHistory(posTail);
        for (String headStep : headSteps) {
            Matcher m = regexPattern.matcher(headStep);
            if (m.find())
                moveHeadAndTail(getHeadDirection(m.group(1)), getSteps(m.group(2)));
        }
        return tailHistory.size();
    }

    public LinkedList<String> getRopeHistory() {
        return ropeHistory;
    }

    public int processStepsForRope(List<String> headSteps) {
        addPosTailToHistory(rope.getTail().getXY());
        for (String headStep : headSteps) {
            Matcher m = regexPattern.matcher(headStep);
            if (m.find()) {
                moveRope(getHeadDirection(m.group(1)), getSteps(m.group(2)));
                ropeHistory.add(rope.toString());
            }
        }
        return tailHistory.size();
    }

    private void moveRope(int[] direction, int steps) {
        for (int i = 1; i <= steps; i++) {
            rope.moveHead(direction);
            moveRope();
            addPosTailToHistory(rope.getTail().getXY());
        }
    }

    private void moveRope() {
        int index = 1;
        while (index <= rope.MAX_INDEX
                && !isTailAdjacent(rope.getElementAt(index - 1).getXY(), rope.getElementAt(index).getXY())) {
            rope.moveElement(index);
            index++;
        }
    }

    private void moveHeadAndTail(int[] direction, int steps) {
        for (int i = 1; i <= steps; i++) {
            int[] newPosHead = moveHead(direction, posHead);
            if (!isTailAdjacent(newPosHead, posTail)) {
                posTail = posHead;
                addPosTailToHistory(posTail);
            }
            posHead = newPosHead;
        }
    }

    private void addPosTailToHistory(int[] posTail) {
        tailHistory.add(String.format("%s, %s", posTail[0], posTail[1]));
    }

    private int[] moveHead(int[] direction, int[] posHead) {
        return new int[]{posHead[0]+direction[0], posHead[1]+direction[1]};
    }

    private boolean isTailAdjacent(int[] posHead, int[] posTail) {
        return !(Math.abs(posTail[0] - posHead[0]) > 1) && !(Math.abs(posTail[1] - posHead[1]) > 1);
    }

    private int[] getHeadDirection(String direction) {
        switch (direction) {
            case "L": return new int[]{-1, 0};
            case "R": return new int[]{1, 0};
            case "U": return new int[]{0, 1};
            case "D": return new int[]{0, -1};
            default: return new int[]{0,0};
        }
    }

    private int getSteps(String steps) {
        return Integer.parseInt(steps);
    }

}

