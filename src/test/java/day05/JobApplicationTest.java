package day05;

import day11.InvalidApplicationStateException;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JobApplicationTest {

    @Test
    void validCreationWorks() {
        Money expectedSalary = new Money(5_000_00L ,"EUR" );
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000010");
        LocalDate appliedDate = LocalDate.of(2026,1,1);
        JobApplication app = JobApplicationBuilder.aDefaultApplication().withCompany("ACME Corp")
                .withRole("Backend Engineer")
                .withStatus(ApplicationStatus.APPLIED)
                .withExpectedSalary(expectedSalary)
                .withAppliedDate(appliedDate)
                .withId(id)
                .build();
        assertEquals("ACME Corp" , app.company());
        assertEquals("Backend Engineer" , app.role());
        assertEquals(ApplicationStatus.APPLIED , app.status());
        assertEquals(appliedDate , app.appliedDate());
        assertEquals(expectedSalary , app.expectedSalary());
        assertEquals(id , app.id());
    }

    @Test
    void blankCompanyThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                JobApplicationBuilder.aDefaultApplication().withCompany(" ").build());
    }

    @Test
    void blankRoleThrows() {
        UUID id = UUID.randomUUID();
        Money expectedSalary = new Money(5_000_00L, "EUR");
        assertThrows(IllegalArgumentException.class, () ->
                JobApplicationBuilder.aDefaultApplication().withRole(" ").build());
    }

    @Test
    void futureAppliedDateThrows() {
        LocalDate appliedDate = LocalDate.of(2027 , 1 , 1);
        assertThrows(IllegalArgumentException.class, () ->
                JobApplicationBuilder.aDefaultApplication().withAppliedDate(appliedDate).build());
    }

    @Test
    void isActiveTrueForAppliedAndInterviewing() {
        JobApplication applied = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.APPLIED).build();
        JobApplication interviewing = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.INTERVIEWING).build();

        assertTrue(applied.isActive());
        assertTrue(interviewing.isActive());
    }

    @Test
    void isActiveFalseForOfferAndRejected() {
        JobApplication rejected = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.REJECTED).build();
        JobApplication offer = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.OFFER).build();

        assertFalse(offer.isActive());
        assertFalse(rejected.isActive());
    }

    @Test
    void withStatusReturnsNewInstanceWithUpdatedStatus() {
       JobApplication original = JobApplicationBuilder.aDefaultApplication().build();

        JobApplication updated = original.withStatus(ApplicationStatus.INTERVIEWING);

        assertEquals(ApplicationStatus.INTERVIEWING, updated.status());
        assertEquals(ApplicationStatus.APPLIED, original.status());
        assertNotSame(original, updated);
    }
    @Test
    void appliedToOffer_shouldThrowInvalidApplicationStateException() {
        JobApplication original = JobApplicationBuilder.aDefaultApplication().build();
        InvalidApplicationStateException ex = assertThrows(
                InvalidApplicationStateException.class,
                () -> original.withStatus(ApplicationStatus.OFFER)
        );
        assertTrue(ex.getMessage().contains("APPLIED"));
        assertTrue(ex.getMessage().contains("OFFER"));
    }
    @Test
    void rejectedToApplied_shouldThrowInvalidApplicationStateException() {
        JobApplication original = JobApplicationBuilder.aDefaultApplication()
                .withStatus(ApplicationStatus.REJECTED).build();
        InvalidApplicationStateException ex = assertThrows(
                InvalidApplicationStateException.class,
                () -> original.withStatus(ApplicationStatus.APPLIED)
        );
        assertTrue(ex.getMessage().contains("final state"));
        assertTrue(ex.getMessage().contains("REJECTED"));
    }


}
