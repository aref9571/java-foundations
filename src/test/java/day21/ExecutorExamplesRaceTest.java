package day21;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class ExecutorExamplesRaceTest {
    @Test
    void unsafeIncrement_usuallyLessThanTwoTimesIterations() throws InterruptedException{
        int iteration = 100_000;
        int result = ExecutorExamplesRace.unsafeIncrement(iteration);

        assertTrue(result <= 2 * iteration);
        System.out.println("unsafeIncrement result = " + result);
    }
    @Test
    void safeIncrement_reachesTwoTimesIterations() throws InterruptedException {
        int iteration = 100_000;
        int result = ExecutorExamplesRace.safeIncrement(iteration);
        assertEquals(2 * iteration , result);
    }
}
