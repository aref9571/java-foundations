package day14;

import static org.junit.jupiter.api.Assertions.*;

import day05.JobApplication;
import day05.JobApplicationBuilder;
import org.junit.jupiter.api.*;

import java.util.UUID;

class JobApplicationCsvConverterTest {
    @Test
    void toLineAndFromLine_roundTrip_withSalary(){
        JobApplication application = JobApplicationBuilder.aDefaultApplication().build();
        String toLineToString = JobApplicationCsvConverter.toLine(application);
        JobApplication loaded = JobApplicationCsvConverter.fromLine(toLineToString);

        assertEquals(application, loaded);
        assertEquals(application.id() , loaded.id());
    }

    @Test
    void toLineAndFromLine_roundTrip_withNullSalary(){
        JobApplication application = JobApplicationBuilder.aDefaultApplication().withExpectedSalary(null).build();
        String toLineToString = JobApplicationCsvConverter.toLine(application);
        JobApplication loaded = JobApplicationCsvConverter.fromLine(toLineToString);

        assertNull(application.expectedSalary());
        assertNull(loaded.expectedSalary());
        assertEquals(application , loaded);
        assertEquals(application.id() , loaded.id());
    }

    @Test
    void fromLine_throws_whenLineIsNullOrBlank(){
        String lineIsBlank = " ";
        String lineIsNull = null;

        assertThrows(IllegalArgumentException.class , ()-> JobApplicationCsvConverter.fromLine(lineIsBlank));
        assertThrows(IllegalArgumentException.class , ()-> JobApplicationCsvConverter.fromLine(lineIsNull));

    }

    @Test
    void fromLine_throws_whenFieldCountIsNotSeven() {
        String badLine = "id|company|role|APPLIED|2026-04-11|7500000";
        assertThrows(IllegalArgumentException.class,()-> JobApplicationCsvConverter.fromLine(badLine));

    }
    @Test
    void fromLine_throws_whenUuidIsInvalid() {
        String badLine = "not-a-uuid|Company|Role|APPLIED|2026-04-11|7500000|EUR";

        assertThrows(RuntimeException.class,
                () -> JobApplicationCsvConverter.fromLine(badLine));
    }

    @Test
    void fromLine_throws_whenDateIsInvalid() {
        String badLine = UUID.randomUUID() +
                "|Company|Role|APPLIED|not-a-date|7500000|EUR";

        assertThrows(RuntimeException.class,
                () -> JobApplicationCsvConverter.fromLine(badLine));
    }

    @Test
    void fromLine_throws_whenStatusIsInvalid() {
        String badLine = UUID.randomUUID() +
                "|Company|Role|NOT_A_STATUS|2026-04-11|7500000|EUR";

        assertThrows(RuntimeException.class,
                () -> JobApplicationCsvConverter.fromLine(badLine));
    }
}