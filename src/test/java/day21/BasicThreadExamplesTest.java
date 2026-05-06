package day21;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
public class BasicThreadExamplesTest {
    @Test
    void printTaskComplete() throws InterruptedException{
        Thread t = BasicThreadExamples.startPrintTasks("test" , 3);
        t.join(1000);
        assertFalse(t.isAlive());
    }
}
