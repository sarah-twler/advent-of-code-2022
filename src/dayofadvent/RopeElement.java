package src.dayofadvent;

class RopeElement {

    private int x;
    private int y;

    public RopeElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RopeElement))
            return false;
        RopeElement e = (RopeElement) o;

        return e.getX() == this.x && e.getY() == this.y;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", x, y);
    }

    public int[] getXY() {
        return new int[]{x, y};
    }

    public void move(int[] direction) {
        this.x = x + direction[0];
        this.y = y + direction[1];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
