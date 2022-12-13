package src.dayofadvent;

import src.utils.FileReader;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignalStrengthCalculator {

    private static final List<Integer> cyclesOfInterest = List.of(20, 60, 100, 140, 180, 220);
    private static final Pattern regex = Pattern.compile("addx\\s(-?\\d+)");
    private static final String NOOP_LINE = "noop";
    private static final int CYCLE_LENGTH_NOOP = 1;
    private static final int CYCLE_LENGTH_ADD_X = 2;
    private static final int WIDTH_CRT = 40;

    public static int getSumSignalStrength(String programFilename) throws IOException {
        List<String> programLines = FileReader.readFileToLines(programFilename);
        return executeProgram(programLines);
    }

    public static String getCrtScreen(String programFilename) throws IOException {
        List<String> programLines = FileReader.readFileToLines(programFilename);
        return drawCrt(programLines);
    }

    private static String drawCrt(List<String> programLines) {
        int spritePos = 1;
        String crtScreen = "";
        String crtLine = "";
        for (String programLine : programLines) {
            int cycleLength = programLine.matches(NOOP_LINE) ? CYCLE_LENGTH_NOOP : CYCLE_LENGTH_ADD_X;
            Matcher m = regex.matcher(programLine);
            int increaseBy = m.find() ? Integer.valueOf(m.group(1)) : 0;
            while (cycleLength > 0) {
                if (crtLine.length() == WIDTH_CRT) {
                    crtScreen += crtLine + System.lineSeparator();
                    crtLine = "";
                }
                crtLine += getPixel(spritePos, crtLine.length());
                cycleLength--;
            }
            spritePos += increaseBy;
        }
        return crtScreen + crtLine + System.lineSeparator();
    }

    private static char getPixel(int spritePos, int currentPos) {
        return spritePos - 1 <= currentPos && currentPos <= spritePos + 1 ? '#' : '.';
    }

    private static int executeProgram(List<String> programLines) {
        int signalStrength = 0;
        int cycleNo = 0;
        int registerX = 1;
        for (String programLine : programLines) {
            int cycleLength = programLine.matches(NOOP_LINE) ? CYCLE_LENGTH_NOOP : CYCLE_LENGTH_ADD_X;
            Matcher m = regex.matcher(programLine);
            int increaseBy = m.find() ? Integer.valueOf(m.group(1)) : 0;
            while (cycleLength > 0) {
                cycleNo++;
                if (cyclesOfInterest.contains(cycleNo))
                    signalStrength += cycleNo * registerX;
                cycleLength--;
            }
            registerX += increaseBy;
        }
        return signalStrength;
    }


}
