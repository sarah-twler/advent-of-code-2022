package src.dayofadvent;

public enum RoundOutcome {
    WIN(6),
    LOOSE(0),
    DRAW(3);

    private int outcomeScore;

    RoundOutcome(int outcomeScore) {
        this.outcomeScore = outcomeScore;
    }

    public static RoundOutcome valueOfOutcomeSymbol(String outcomeSymbol) throws IllegalAccessException {
        switch (outcomeSymbol) {
            case "X":
                return LOOSE;
            case "Y":
                return DRAW;
            case "Z":
                return WIN;
            default:
                throw new IllegalAccessException("Unknown outcome symbol: " + outcomeSymbol);
        }
    }

    public int getOutcomeScore() {
        return outcomeScore;
    }
}