package day26;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

class ConsoleLoggerTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void redirectOutput() {
        originalOut = System.out;
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void info_replacesPlaceholdersInOrder() {
        ConsoleLogger logger = new ConsoleLogger(ConsoleLoggerTest.class);

        logger.info("total={} active={}", 4, 2);

        assertEquals("[INFO] [ConsoleLoggerTest] total=4 active=2", printedLine());
    }

    @Test
    void info_printsPercentSignWithoutFailing() {
        ConsoleLogger logger = new ConsoleLogger(ConsoleLoggerTest.class);

        logger.info("rate 50% for {}", "100% Corp");

        assertEquals("[INFO] [ConsoleLoggerTest] rate 50% for 100% Corp", printedLine());
    }

    @Test
    void info_keepsExtraPlaceholdersWhenArgumentsAreMissing() {
        ConsoleLogger logger = new ConsoleLogger(ConsoleLoggerTest.class);

        logger.info("value={} other={}", 1);

        assertEquals("[INFO] [ConsoleLoggerTest] value=1 other={}", printedLine());
    }

    private String printedLine() {
        return output.toString(StandardCharsets.UTF_8).strip();
    }
}
