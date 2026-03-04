package day05;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class InterviewSlotTest {

    @Test
    void validSlotIsAccepted() {
        InterviewSlot slot = new InterviewSlot(
                LocalDate.now(),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                true
        );

        assertEquals(LocalDate.now(), slot.date());
        assertEquals(LocalTime.of(10, 0), slot.startTime());
        assertEquals(LocalTime.of(11, 0), slot.endTime());
        assertTrue(slot.remote());
        assertEquals(Duration.ofHours(1), slot.duration());
    }

    @Test
    void endBeforeStartThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new InterviewSlot(
                        LocalDate.now(),
                        LocalTime.of(11, 0),
                        LocalTime.of(10, 0),
                        false
                ));
    }

    @Test
    void pastDateThrows() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        assertThrows(IllegalArgumentException.class, () ->
                new InterviewSlot(
                        yesterday,
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0),
                        false
                ));
    }

    @Test
    void overlappingSlotsOnSameDateReturnTrue() {
        LocalDate today = LocalDate.now();

        InterviewSlot first = new InterviewSlot(
                today,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                true
        );
        InterviewSlot second = new InterviewSlot(
                today,
                LocalTime.of(10, 30),
                LocalTime.of(11, 30),
                false
        );

        assertTrue(first.overlaps(second));
        assertTrue(second.overlaps(first));
    }

    @Test
    void touchingSlotsDoNotOverlap() {
        LocalDate today = LocalDate.now();

        InterviewSlot first = new InterviewSlot(
                today,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                true
        );
        InterviewSlot second = new InterviewSlot(
                today,
                LocalTime.of(11, 0),
                LocalTime.of(12, 0),
                false
        );

        assertFalse(first.overlaps(second));
        assertFalse(second.overlaps(first));
    }

    @Test
    void differentDatesDoNotOverlap() {
        InterviewSlot first = new InterviewSlot(
                LocalDate.now(),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                true
        );
        InterviewSlot second = new InterviewSlot(
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 30),
                LocalTime.of(11, 30),
                false
        );

        assertFalse(first.overlaps(second));
    }
}
