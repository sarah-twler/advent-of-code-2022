package src.dayofadvent;

public enum HandShape {

    ROCK(1),
    PAPER(2),
    SCISSOR(3);

    private final int handScore;

    HandShape(int handScore) {
        this.handScore = handScore;
    }

    public static HandShape valueOfHandSymbol(String handSymbol) throws IllegalAccessException {
        switch (handSymbol) {
            case "A":
            case "X":
                return ROCK;
            case "B":
            case "Y":
                return PAPER;
            case "C":
            case "Z":
                return SCISSOR;
            default:
                throw new IllegalAccessException("Unknown hand symbol: " + handSymbol);
        }
    }

    public int getHandScore() {
        return handScore;
    };
}
