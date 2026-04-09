package day12;
import static org.junit.jupiter.api.Assertions.*;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.Money;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.UUID;

class BoxTest {
    @Test
    void stringBoxStoresAndReturnsValue(){
        Box<String> box = new Box<>("Hello");
        String value = box.getValue();

        assertEquals("Hello" , value);
        assertFalse(box.isEmpty());
    }

    @Test
    void jobApplicationBoxStoresAndReturnValue(){
        UUID id = UUID.randomUUID();
        JobApplication application = new JobApplication(
                "Meta",
                "Backend Engineer",
                ApplicationStatus.APPLIED,
                LocalDate.now().minusDays(1),
                new Money(4_000L, "EUR"),
                id
        );
        Box<JobApplication> box = new Box<>(application);
        JobApplication value = box.getValue();

        assertSame(application , value);
        assertFalse(box.isEmpty());
    }
    @Test
    void boxIsEmptyWhenValueIsNull() {
        Box<String> box = new Box<>(null);

        assertTrue(box.isEmpty());
        assertNull(box.getValue());
    }

    @Test
    void boxIsNotEmptyWhenValueExists() {
        Box<Integer> box = new Box<>(42);

        assertFalse(box.isEmpty());
    }
}
