package src.dayofadvent;

import src.utils.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class SignalProcessor {

    private static final int SIGNAL_LENGTH = 4;
    private static final int MESSAGE_LENGTH = 14;

    public static int getNumberOfCharactersBeforeSignal(String dataStreamFilename) throws IOException {
        List<String> dataStream = FileReader.readFileToLines(dataStreamFilename);
        if (dataStream.size() != 1)
            System.out.println("Received unexpected multiline input!");
        return findMarker(dataStream.get(0));
    }

    public static int getNumberOfCharactersBeforeMessage(String dataStreamFilename) throws IOException {
        List<String> dataStream = FileReader.readFileToLines(dataStreamFilename);
        if (dataStream.size() != 1)
            System.out.println("Received unexpected multiline input!");
        return findSignal(dataStream.get(0));
    }

    public static int findSignal(String dataStream) {
        return findPosition(dataStream, MESSAGE_LENGTH);
    }

    public static int findMarker(String dataStream) {
        return findPosition(dataStream, SIGNAL_LENGTH);
    }

    public static int findPosition(String dataStream, int blockLength) {
        for (int i = 0; i < dataStream.length(); i++)
            if (isSignalBeforeMarker(i, dataStream, blockLength))
                return i + blockLength;
        return -1;
    }

    private static boolean isSignalBeforeMarker(int pointer, String dataStream, int blockLength){
        Set<Character> s = new HashSet();
        char[] c = dataStream.substring(pointer, pointer + blockLength).toCharArray();
        IntStream.range(0, blockLength).forEach(index -> s.add(c[index]));
        return s.size() == blockLength;
    }

}

