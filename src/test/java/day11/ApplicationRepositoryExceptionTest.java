package day11;
import static org.junit.jupiter.api.Assertions.*;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import day05.Money;
import day10.ApplicationRepository;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

class ApplicationRepositoryExceptionTest {

    private ApplicationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ApplicationRepository();
    }

    @Test
    void findByIdOrThrow_unknownId_shouldThrowApplicationNotFoundException() {
        JobApplication app = JobApplicationBuilder.aDefaultApplication().build();
        repository.add(app);

        UUID unknown = UUID.randomUUID();

        ApplicationNotFoundException ex = assertThrows(
                ApplicationNotFoundException.class,
                () -> repository.findByIdOrThrow(unknown)
        );
        assertTrue(ex.getMessage().contains("No application found with id: " + unknown));
    }

    @Test
    void findByIdOrThrow_nullId_shouldThrowIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> repository.findByIdOrThrow(null)
        );
        assertTrue(ex.getMessage().contains("id must not be null"));
    }

    @Test
    void add_duplicateCompanyAndRole_shouldThrowDuplicateApplicationException() {

        JobApplication first = JobApplicationBuilder.aDefaultApplication().build();
        JobApplication duplicate = JobApplicationBuilder.aDefaultApplication().build();

        repository.add(first);

        DuplicateApplicationException ex = assertThrows(
                DuplicateApplicationException.class,
                () -> repository.add(duplicate)
        );
        assertTrue(ex.getMessage().contains("DefaultCorp"));
        assertTrue(ex.getMessage().contains("Backend Engineer"));
    }

    @Test
    void updateStatus_unknownId_shouldThrowApplicationNotFoundException() {
        UUID unknown = UUID.randomUUID();

        ApplicationNotFoundException ex = assertThrows(
                ApplicationNotFoundException.class,
                () -> repository.updateStatus(unknown, ApplicationStatus.INTERVIEWING)
        );
        assertTrue(ex.getMessage().contains("No application found with id: " + unknown));
    }

    @Test
    void updateStatus_invalidTransition_shouldThrowInvalidApplicationStateException() {
        JobApplication rejected = JobApplicationBuilder.aDefaultApplication().withStatus(ApplicationStatus.REJECTED).build();
        UUID id = rejected.id();
        repository.add(rejected);

        InvalidApplicationStateException ex = assertThrows(
                InvalidApplicationStateException.class,
                () -> repository.updateStatus(id, ApplicationStatus.APPLIED)
        );
        assertTrue(ex.getMessage().toUpperCase().contains("FINAL"));
    }

    @Test
    void updateStatus_validTransition_shouldUpdateRepositoryAndReturnUpdatedApplication() {
        JobApplication applied = JobApplicationBuilder.aDefaultApplication().withStatus(ApplicationStatus.APPLIED).build();
        UUID id = applied.id();
        repository.add(applied);

        JobApplication updated = repository.updateStatus(id, ApplicationStatus.INTERVIEWING);

        assertEquals(ApplicationStatus.INTERVIEWING, updated.status());
        assertEquals(id, updated.id());

        assertTrue(repository.findByStatus(ApplicationStatus.INTERVIEWING).contains(updated));
        assertFalse(repository.findByStatus(ApplicationStatus.APPLIED).contains(updated));

    }
}
