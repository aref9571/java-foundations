package day07;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JobTrackerServiceTest {

    private JobTrackerService service;

    @BeforeEach
    void setUp() {
        service = new JobTrackerService();
        service.clear();
    }

    @Test
    void addApplicationStoresAndListsIt() {
        String company = "Amazon";
        String role = "Engineer";
        ApplicationStatus status = ApplicationStatus.APPLIED;
        LocalDate appliedDate = LocalDate.now();
        Money expectedSalary = new Money(1000L, "EUR");

        JobApplication created = service.addApplication(company, role, status, appliedDate, expectedSalary);
        List<JobApplication> applications = service.listApplications();

        assertEquals(1, applications.size());
        JobApplication stored = applications.get(0);

        assertEquals(company, stored.company());
        assertEquals(role, stored.role());
        assertEquals(status, stored.status());
        assertEquals(appliedDate, stored.appliedDate());
        assertEquals(expectedSalary, stored.expectedSalary());
        assertEquals(created.id(), stored.id());
    }

    @Test
    void findByStatus_returnsOnlyMatchingStatus() {
        service.addApplication("Amazon", "Data Scientist", ApplicationStatus.APPLIED,
                LocalDate.now(), new Money(1000L, "EUR"));
        service.addApplication("Google", "Software Engineer", ApplicationStatus.REJECTED,
                LocalDate.now(), new Money(1000L, "EUR"));
        service.addApplication("Microsoft", "Engineer", ApplicationStatus.APPLIED,
                LocalDate.now(), new Money(1000L, "EUR"));

        List<JobApplication> result = service.findByStatus(ApplicationStatus.APPLIED);

        assertEquals(2, result.size());
        for (JobApplication app : result) {
            assertEquals(ApplicationStatus.APPLIED, app.status());
        }

        List<String> companies = result.stream()
                .map(JobApplication::company)
                .toList();

        assertTrue(companies.contains("Amazon"));
        assertTrue(companies.contains("Microsoft"));
        assertFalse(companies.contains("Google"));
    }

    @Test
    void findByCompany_returnsAllApplicationsForCompany() {
        service.addApplication("Amazon", "Data Scientist", ApplicationStatus.APPLIED,
                LocalDate.now(), new Money(1000L, "EUR"));
        service.addApplication("Google", "Software Engineer", ApplicationStatus.REJECTED,
                LocalDate.now(), new Money(1000L, "EUR"));
        service.addApplication("Google", "Engineer", ApplicationStatus.APPLIED,
                LocalDate.now(), new Money(1000L, "EUR"));

        List<JobApplication> result = service.findByCompany("Google");

        assertEquals(2, result.size());
        for (JobApplication app : result) {
            assertEquals("Google", app.company());
        }
    }

    @Test
    void findByCompany_isCaseInsensitive() {
        service.addApplication("Google", "Engineer", ApplicationStatus.APPLIED,
                LocalDate.now(), new Money(1000L, "EUR"));

        List<JobApplication> resultLower = service.findByCompany("google");
        List<JobApplication> resultUpper = service.findByCompany("GOOGLE");

        assertEquals(1, resultLower.size());
        assertEquals(1, resultUpper.size());
        assertEquals("Google", resultLower.get(0).company());
        assertEquals("Google", resultUpper.get(0).company());
    }

    @Test
    void findByCompany_whenCompanyDoesNotExist_returnsEmpty() {
        service.addApplication("Amazon", "Data Scientist", ApplicationStatus.APPLIED,
                LocalDate.now(), new Money(1000L, "EUR"));

        List<JobApplication> result = service.findByCompany("Netflix");

        assertTrue(result.isEmpty());
    }

    @Test
    void updateStatus_changesStatusForExistingApplication() {
        JobApplication created = service.addApplication("Amazon", "Engineer", ApplicationStatus.APPLIED,
                LocalDate.now(), new Money(1000L, "EUR"));
        UUID id = created.id();

        JobApplication updated = service.updateStatus(id, ApplicationStatus.INTERVIEWING);

        assertEquals(id, updated.id());
        assertEquals(ApplicationStatus.INTERVIEWING, updated.status());

        List<JobApplication> applications = service.listApplications();
        assertEquals(1, applications.size());
        assertEquals(ApplicationStatus.INTERVIEWING, applications.get(0).status());
    }

    @Test
    void updateStatus_throwsWhenIdNotFound() {
        UUID unknownId = UUID.randomUUID();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.updateStatus(unknownId, ApplicationStatus.OFFER)
        );

        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void updateStatus_throwsWhenIdIsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.updateStatus(null, ApplicationStatus.OFFER)
        );

        assertTrue(ex.getMessage().contains("id must not be null"));
    }

    @Test
    void updateStatus_throwsWhenNewStatusIsNull() {
        JobApplication created = service.addApplication("Amazon", "Engineer", ApplicationStatus.APPLIED,
                LocalDate.now(), new Money(1000L, "EUR"));

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.updateStatus(created.id(), null)
        );

        assertTrue(ex.getMessage().contains("newStatus must not be null"));
    }

    @Test
    void addApplication_throwsWhenCompanyBlank() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.addApplication("  ", "Engineer", ApplicationStatus.APPLIED,
                        LocalDate.now(), new Money(1000L, "EUR"))
        );

        assertTrue(ex.getMessage().contains("company must be non-blank"));
    }

    @Test
    void addApplication_throwsWhenAppliedDateInFuture() {
        LocalDate futureDate = LocalDate.now().plusDays(1);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.addApplication("Amazon", "Engineer", ApplicationStatus.APPLIED,
                        futureDate, new Money(1000L, "EUR"))
        );

        assertTrue(ex.getMessage().contains("appliedDate must not be in the future"));
    }
}
