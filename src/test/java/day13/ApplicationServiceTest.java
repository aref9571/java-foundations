package day13;

import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import day05.Money;
import day10.ApplicationRepository;
import day11.ApplicationNotFoundException;
import day11.InvalidApplicationStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationServiceTest {

    private ApplicationRepository repository;
    private ApplicationService service;

    @BeforeEach
    void setUp() {
        repository = new ApplicationRepository();
        service = new ApplicationService(repository);
    }

    @Test
    void applyForJob_createsAndStoresApplication() {
        Money expectedSalary = new Money(1000L, "EUR");
        LocalDate appliedDate = LocalDate.now().minusDays(1);

        JobApplication created = service.applyForJob("Amazon", "Backend", appliedDate, expectedSalary);
        List<JobApplication> allApplications = repository.findAll();

        assertEquals(1, allApplications.size());
        JobApplication stored = allApplications.get(0);
        assertEquals(created.company(), stored.company());
        assertEquals(ApplicationStatus.APPLIED, stored.status());
        assertEquals(appliedDate, stored.appliedDate());
        assertEquals(expectedSalary, stored.expectedSalary());
    }

    @Test
    void applyForJob_rejectsBlankCompany() {
        Money expectedSalary = new Money(1000L, "EUR");
        LocalDate appliedDate = LocalDate.now().minusDays(1);

        assertThrows(IllegalArgumentException.class,
                () -> service.applyForJob(null, "Backend", appliedDate, expectedSalary));
        assertThrows(IllegalArgumentException.class,
                () -> service.applyForJob("   ", "Backend", appliedDate, expectedSalary));
    }

    @Test
    void getAllApplications_returnsAllStoredApplications() {
        Money expectedSalary = new Money(1000L, "EUR");
        LocalDate appliedDate = LocalDate.now().minusDays(1);

        JobApplication app1 = service.applyForJob("Amazon", "Backend", appliedDate, expectedSalary);
        JobApplication app2 = service.applyForJob("Google", "Engineer", appliedDate.minusDays(2), expectedSalary);

        List<JobApplication> allApplications = service.getAllApplications();

        assertEquals(2, allApplications.size());
        assertTrue(allApplications.contains(app1));
        assertTrue(allApplications.contains(app2));
    }

    @Test
    void findByCompany_returnsOnlyMatchingApplications() {
        Money expectedSalary = new Money(1000L, "EUR");
        LocalDate appliedDate = LocalDate.now().minusDays(1);

        service.applyForJob("Amazon", "Backend", appliedDate, expectedSalary);
        service.applyForJob("Google", "Engineer", appliedDate.minusDays(2), expectedSalary);
        service.applyForJob("Google", "Backend", appliedDate.minusDays(3), expectedSalary);

        List<JobApplication> result = service.findByCompany("Google");

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(app -> "Google".equals(app.company())));
    }

    @Test
    void findByStatus_returnsOnlyMatchingApplications() {


        JobApplication app1 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Meta").withStatus(ApplicationStatus.REJECTED).build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Google").withStatus(ApplicationStatus.APPLIED).build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication()
                .withCompany("Amazon").withStatus(ApplicationStatus.APPLIED).build();

        repository.add(app1);
        repository.add(app2);
        repository.add(app3);

        List<JobApplication> result = service.findByStatus(ApplicationStatus.APPLIED);

        assertEquals(2, result.size());
        assertFalse(result.contains(app1));
        assertTrue(result.stream().allMatch(app -> ApplicationStatus.APPLIED.equals(app.status())));
        assertTrue(result.stream().anyMatch(app -> "Google".equals(app.company())));
        assertTrue(result.stream().anyMatch(app -> "Amazon".equals(app.company())));
    }

    @Test
    void moveToInterviewing_updatesStatusAndPersists() {
        Money expectedSalary = new Money(1000L, "EUR");
        LocalDate appliedDate = LocalDate.now().minusDays(1);

        JobApplication created = service.applyForJob("Amazon", "Backend", appliedDate, expectedSalary);
        UUID id = created.id();

        JobApplication updated = service.moveToInterviewing(id);

        assertEquals(ApplicationStatus.INTERVIEWING, updated.status());

        List<JobApplication> interviewing = service.findByStatus(ApplicationStatus.INTERVIEWING);
        List<JobApplication> applied = service.findByStatus(ApplicationStatus.APPLIED);

        assertTrue(interviewing.contains(updated));
        assertFalse(applied.contains(updated));
    }

    @Test
    void moveToInterviewing_throwsWhenIdUnknown() {
        UUID unknownId = UUID.randomUUID();

        assertThrows(ApplicationNotFoundException.class,
                () -> service.moveToInterviewing(unknownId));
    }

    @Test
    void reject_throwsOnIllegalTransition() {
        Money expectedSalary = new Money(1000L, "EUR");
        LocalDate appliedDate = LocalDate.now().minusDays(1);

        JobApplication created = service.applyForJob("Amazon", "Backend", appliedDate, expectedSalary);
        JobApplication firstRejected = service.reject(created.id());

        assertEquals(ApplicationStatus.REJECTED, firstRejected.status());

        assertThrows(InvalidApplicationStateException.class,
                () -> service.reject(created.id()));
    }

    @Test
    void constructor_rejectsNullRepository() {
        assertThrows(NullPointerException.class,
                () -> new ApplicationService(null));
    }

    @Test
    void findApplicationByCompany_returnsPresentWhenCompanyHasApplications() {
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication().withCompany("Amazon").withRole("NA").withStatus(ApplicationStatus.INTERVIEWING).build();
        repository.add(app1);
        repository.add(app2);
        Optional<JobApplication> result = service.findApplicationByCompany("Amazon");
        assertTrue(result.isPresent());
        assertEquals("Amazon" , result.get().company());
    }

    @Test
    void findApplicationByCompany_returnsEmptyWhenCompanyHasNoApplications(){
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().withCompany("Amazon").withRole("NA").withStatus(ApplicationStatus.INTERVIEWING).build();
        repository.add(app1);
        Optional<JobApplication> result = service.findApplicationByCompany("Goggle");
        assertTrue(result.isEmpty());

    }
    @Test
    void findApplicationByCompany_throwsIllegalArgumentExceptionWhenCompanyIsBlank(){
        assertThrows(IllegalArgumentException.class,() -> service.findApplicationByCompany(" "));
    }
}