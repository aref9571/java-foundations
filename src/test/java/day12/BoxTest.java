package day12;
import static org.junit.jupiter.api.Assertions.*;

import day05.JobApplication;
import day05.JobApplicationBuilder;
import org.junit.jupiter.api.*;


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
        JobApplication application = JobApplicationBuilder.aDefaultApplication().build();
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
