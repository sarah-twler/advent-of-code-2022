package src.dayofadvent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rope {

    private LinkedList<RopeElement> elements;
    private final int SIZE = 10;
    public final int MAX_INDEX = SIZE - 1;

    public Rope() {
        elements = IntStream.range(0, SIZE).mapToObj(e -> new RopeElement(0, 0)).collect(Collectors.toCollection(LinkedList::new));
    }

    public Rope(int[][] ropeCoordinates) {
        elements = Arrays.stream(ropeCoordinates).map(c -> new RopeElement(c[0], c[1])).collect(Collectors.toCollection(LinkedList::new));
    }

    public RopeElement getTail() {
        return elements.getLast();
    }

    public RopeElement getElementAt(int index) {
        return elements.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Rope))
            return false;
        Rope e = (Rope) o;

        return elements.equals(e);
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    /**
     * @return old head position
     */
    public RopeElement moveHead(int[] direction) {
        RopeElement oldHead = new RopeElement(elements.getFirst().getX(), elements.getFirst().getY());
        elements.getFirst().move(direction);
        return oldHead;
    }

    public boolean moveElement(int index) {
        RopeElement ahead = getElementAt(index - 1);
        RopeElement oldAhead = new RopeElement(ahead.getX(), ahead.getY());
        RopeElement actual = getElementAt(index);

        int[] direction = getKeepUpDirection(ahead, actual);
        actual.move(direction);

        return oldAhead.equals(ahead);
    }

    private int[] getKeepUpDirection(RopeElement ahead, RopeElement actual) {
        int diffX = ahead.getX() - actual.getX();
        int dirX = diffX == 0 ? diffX : diffX > 0 ? 1 : -1;
        int diffY = ahead.getY() - actual.getY();
        int dirY = diffY == 0 ? diffY : diffY > 0 ? 1 : -1;
        return new int[]{dirX, dirY};
    }

}
