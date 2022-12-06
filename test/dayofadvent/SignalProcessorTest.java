package test.dayofadvent;

import org.testng.annotations.Test;
import src.dayofadvent.SignalProcessor;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class SignalProcessorTest {

    static private final int DAY_OF_ADVENT = 6;

    @Test
    public void testFindMarker() throws IOException {
        assertEquals(SignalProcessor.findMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 7);
        assertEquals(SignalProcessor.findMarker("bvwbjplbgvbhsrlpgdmjqwftvncz"), 5);
        assertEquals(SignalProcessor.findMarker("nppdvjthqldpwncqszvftbrmjlhg"), 6);
        assertEquals(SignalProcessor.findMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 10);
        assertEquals(SignalProcessor.findMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 11);
    }

    @Test
    public void testFindSignal() {
        assertEquals(SignalProcessor.findSignal("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), 19);
        assertEquals(SignalProcessor.findSignal("bvwbjplbgvbhsrlpgdmjqwftvncz"), 23);
        assertEquals(SignalProcessor.findSignal("nppdvjthqldpwncqszvftbrmjlhg"), 23);
        assertEquals(SignalProcessor.findSignal("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), 29);
        assertEquals(SignalProcessor.findSignal("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), 26);
    }

}