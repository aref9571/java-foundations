package day05;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JobApplicationTest {

    @Test
    void validCreationWorks() {
        Money expectedSalary = new Money(5_000_00L, "EUR");
        UUID id = UUID.randomUUID();
        JobApplication app = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.APPLIED,
                LocalDate.now(),
                expectedSalary,
                id
        );

        assertEquals("ACME Corp", app.company());
        assertEquals("Backend Engineer", app.role());
        assertEquals(ApplicationStatus.APPLIED, app.status());
        assertEquals(LocalDate.now(), app.appliedDate());
        assertEquals(expectedSalary, app.expectedSalary());
        assertEquals(id, app.id());
    }

    @Test
    void blankCompanyThrows() {
        UUID id = UUID.randomUUID();
        // FIX: use non-null Money to respect expectedSalary invariant
        Money expectedSalary = new Money(5_000_00L, "EUR");
        assertThrows(IllegalArgumentException.class, () ->
                new JobApplication(
                        " ",
                        "Backend Engineer",
                        ApplicationStatus.APPLIED,
                        LocalDate.now(),
                        expectedSalary,
                        id
                ));
    }

    @Test
    void blankRoleThrows() {
        UUID id = UUID.randomUUID();
        // FIX: use non-null Money to respect expectedSalary invariant
        Money expectedSalary = new Money(5_000_00L, "EUR");
        assertThrows(IllegalArgumentException.class, () ->
                new JobApplication(
                        "ACME Corp",
                        " ",
                        ApplicationStatus.APPLIED,
                        LocalDate.now(),
                        expectedSalary,
                        id
                ));
    }

    @Test
    void futureAppliedDateThrows() {
        LocalDate future = LocalDate.now().plusDays(1);
        UUID id = UUID.randomUUID();
        // FIX: use non-null Money to respect expectedSalary invariant
        Money expectedSalary = new Money(5_000_00L, "EUR");
        assertThrows(IllegalArgumentException.class, () ->
                new JobApplication(
                        "ACME Corp",
                        "Backend Engineer",
                        ApplicationStatus.APPLIED,
                        future,
                        expectedSalary,
                        id
                ));
    }

    @Test
    void isActiveTrueForAppliedAndInterviewing() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        // FIX: use non-null Money to respect expectedSalary invariant
        Money expectedSalary = new Money(5_000_00L, "EUR");

        JobApplication applied = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.APPLIED,
                LocalDate.now(),
                expectedSalary,
                id1
        );
        JobApplication interviewing = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.INTERVIEWING,
                LocalDate.now(),
                expectedSalary,
                id2
        );

        assertTrue(applied.isActive());
        assertTrue(interviewing.isActive());
    }

    @Test
    void isActiveFalseForOfferAndRejected() {
        UUID id = UUID.randomUUID();
        // FIX: use non-null Money to respect expectedSalary invariant
        Money expectedSalary = new Money(5_000_00L, "EUR");

        JobApplication offer = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.OFFER,
                LocalDate.now(),
                expectedSalary,
                id
        );
        JobApplication rejected = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.REJECTED,
                LocalDate.now(),
                expectedSalary,
                id
        );

        assertFalse(offer.isActive());
        assertFalse(rejected.isActive());
    }

    @Test
    void withStatusReturnsNewInstanceWithUpdatedStatus() {
        UUID id = UUID.randomUUID();
        // FIX: use non-null Money to respect expectedSalary invariant
        Money expectedSalary = new Money(5_000_00L, "EUR");

        JobApplication original = new JobApplication(
                "ACME Corp",
                "Backend Engineer",
                ApplicationStatus.APPLIED,
                LocalDate.now(),
                expectedSalary,
                id
        );

        JobApplication updated = original.withStatus(ApplicationStatus.INTERVIEWING);

        assertEquals(ApplicationStatus.INTERVIEWING, updated.status());
        assertEquals(ApplicationStatus.APPLIED, original.status());
        assertNotSame(original, updated);
    }
}
