package day11;
import static org.junit.jupiter.api.Assertions.*;

import day05.ApplicationStatus;
import day05.JobApplication;
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
        UUID appId = UUID.randomUUID();
        JobApplication app = new JobApplication(
                "Amazon",
                "Backend",
                ApplicationStatus.APPLIED,
                LocalDate.now().minusDays(1),
                new Money(1_000L, "EUR"),
                appId
        );
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
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        JobApplication first = new JobApplication(
                "Amazon",
                "Backend",
                ApplicationStatus.APPLIED,
                LocalDate.now().minusDays(1),
                new Money(1_000L, "EUR"),
                id1
        );
        JobApplication duplicate = new JobApplication(
                "Amazon",
                "Backend",
                ApplicationStatus.APPLIED,
                LocalDate.now().minusDays(2),
                new Money(2_000L, "EUR"),
                id2
        );

        repository.add(first);

        DuplicateApplicationException ex = assertThrows(
                DuplicateApplicationException.class,
                () -> repository.add(duplicate)
        );
        assertTrue(ex.getMessage().contains("Amazon"));
        assertTrue(ex.getMessage().contains("Backend"));
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
        UUID id = UUID.randomUUID();
        JobApplication rejected = new JobApplication(
                "Google",
                "Backend Engineer",
                ApplicationStatus.REJECTED,
                LocalDate.now().minusDays(1),
                new Money(3_000L, "EUR"),
                id
        );
        repository.add(rejected);

        InvalidApplicationStateException ex = assertThrows(
                InvalidApplicationStateException.class,
                () -> repository.updateStatus(id, ApplicationStatus.APPLIED)
        );
        assertTrue(ex.getMessage().toUpperCase().contains("FINAL"));
    }

    @Test
    void updateStatus_validTransition_shouldUpdateRepositoryAndReturnUpdatedApplication() {
        UUID id = UUID.randomUUID();
        JobApplication applied = new JobApplication(
                "Meta",
                "Backend Engineer",
                ApplicationStatus.APPLIED,
                LocalDate.now().minusDays(1),
                new Money(4_000L, "EUR"),
                id
        );
        repository.add(applied);

        JobApplication updated = repository.updateStatus(id, ApplicationStatus.INTERVIEWING);

        assertEquals(ApplicationStatus.INTERVIEWING, updated.status());
        assertEquals(id, updated.id());

        assertTrue(repository.findByStatus(ApplicationStatus.INTERVIEWING).contains(updated));
        assertFalse(repository.findByStatus(ApplicationStatus.APPLIED).contains(updated));

    }
}
