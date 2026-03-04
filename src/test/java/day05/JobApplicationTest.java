package day05;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JobApplicationTest {

    @Test
    void validCreationWorks() {
        Money expectedSalary = new Money(5_000_00L, "EUR");
        JobApplication app = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.APPLIED,
                LocalDate.now(),
                expectedSalary
        );

        assertEquals("ACME Corp", app.company());
        assertEquals("Backend Engineer", app.role());
        assertEquals(ApplicationStatus.APPLIED, app.status());
        assertEquals(LocalDate.now(), app.appliedDate());
        assertEquals(expectedSalary, app.expectedSalary());
    }

    @Test
    void blankCompanyThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new JobApplication(
                        " ",
                        "Backend Engineer",
                        ApplicationStatus.APPLIED,
                        LocalDate.now(),
                        null
                ));
    }

    @Test
    void blankRoleThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new JobApplication(
                        "ACME Corp",
                        " ",
                        ApplicationStatus.APPLIED,
                        LocalDate.now(),
                        null
                ));
    }

    @Test
    void futureAppliedDateThrows() {
        LocalDate future = LocalDate.now().plusDays(1);

        assertThrows(IllegalArgumentException.class, () ->
                new JobApplication(
                        "ACME Corp",
                        "Backend Engineer",
                        ApplicationStatus.APPLIED,
                        future,
                        null
                ));
    }

    @Test
    void isActiveTrueForAppliedAndInterviewing() {
        JobApplication applied = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.APPLIED,
                LocalDate.now(),
                null
        );
        JobApplication interviewing = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.INTERVIEWING,
                LocalDate.now(),
                null
        );

        assertTrue(applied.isActive());
        assertTrue(interviewing.isActive());
    }

    @Test
    void isActiveFalseForOfferAndRejected() {
        JobApplication offer = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.OFFER,
                LocalDate.now(),
                null
        );
        JobApplication rejected = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.REJECTED,
                LocalDate.now(),
                null
        );

        assertFalse(offer.isActive());
        assertFalse(rejected.isActive());
    }

    @Test
    void withStatusReturnsNewInstanceWithUpdatedStatus() {
        JobApplication original = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.APPLIED,
                LocalDate.now(),
                null
        );

        JobApplication updated = original.withStatus(ApplicationStatus.INTERVIEWING);

        assertEquals(ApplicationStatus.INTERVIEWING, updated.status());
        assertEquals(ApplicationStatus.APPLIED, original.status());
        assertNotSame(original, updated);
    }
}
