package day10;
import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import day05.Money;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.*;
class ApplicationRepositoryTest {
    private ApplicationRepository repository;
    @BeforeEach
    void setUp(){
        repository = new ApplicationRepository();
    }
    @Test
    void findAll_returnsAllApplications_andIsUnmodifiable(){
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().withCompany("Meta").build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication().withCompany("Google").build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication().withCompany("Amazon").build();
        repository.add(app1);
        repository.add(app2);
        repository.add(app3);
        List<JobApplication> result = repository.findAll();

        assertEquals(3 , result.size());
        assertTrue(result.contains(app1));
        assertTrue(result.contains(app2));
        assertTrue(result.contains(app3));
        assertThrows(UnsupportedOperationException.class , result::clear);
    }
    @Test
    void findByCompany_unknownCompany_returnsEmptyList(){
        repository.add(JobApplicationBuilder.aDefaultApplication().build());

        List<JobApplication> result = repository.findByCompany("Google");
        assertTrue(result.isEmpty());
    }
    @Test
    void findByCompany_blankCompany_throwsException(){
        assertThrows(IllegalArgumentException.class , () -> repository.findByCompany(null));
        assertThrows(IllegalArgumentException.class , () -> repository.findByCompany(" "));

    }
    @Test
    void findByStatus_returnsOnlyMatchingStatus(){
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().withCompany("Google").withRole("Engineer").build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication().withCompany("Meta").withRole("Software Engineer").build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication().withStatus(ApplicationStatus.REJECTED).build();
        repository.add(app1);
        repository.add(app2);
        repository.add(app3);

        List<JobApplication> result = repository.findByStatus(ApplicationStatus.APPLIED);
        assertEquals(2 , result.size());
        assertTrue(result.contains(app1));
        assertTrue(result.contains(app2));
        assertFalse(result.contains(app3));
    }
    @Test
    void findByStatus_nullStatus_throwsException(){
        assertThrows(IllegalArgumentException.class , () -> repository.findByStatus(null));
    }
    @Test
    void countActive_returnsNumberOfActiveApplications(){
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().build();
        JobApplication app2 = JobApplicationBuilder.aDefaultApplication().withCompany("Amazon").withRole("NA").withStatus(ApplicationStatus.INTERVIEWING).build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication().withCompany("Meta").withRole("Software Engineer").withStatus(ApplicationStatus.REJECTED).build();
        repository.add(app1);
        repository.add(app2);
        repository.add(app3);

        long result = repository.countActive();
        assertEquals(2L , result);
    }

    @Test
    void findById_returnsPresentWhenApplicationExists(){
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().build();
        repository.add(app1);
        Optional<JobApplication> result = repository.findById(app1.id());
        assertTrue(result.isPresent());
        assertEquals(app1, result.get());
        assertEquals(app1 , result.get());

    }
    @Test
    void findById_throwsIllegalArgumentExceptionWhenIdIsNull(){
        assertThrows(IllegalArgumentException.class,() -> repository.findById(null));
    }

    @Test
    void findFirstByCompany_returnsPresentWhenCompanyHasApplications(){
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().withCompany("Amazon").build();
        repository.add(app1);
        Optional<JobApplication> result = repository.findFirstByCompany("amazon");
        assertTrue(result.isPresent());
        assertEquals(app1 , result.get());
    }

    @Test
    void findFirstByCompany_returnsEmptyWhenCompanyHasNoApplications(){
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().build();
        repository.add(app1);
        Optional<JobApplication> result = repository.findFirstByCompany("Amazon");
        assertFalse(result.isPresent());
        assertTrue(result.isEmpty());

    }
    @Test
    void findFirstByCompany_throwsIllegalArgumentExceptionWhenCompanyIsBlank(){
        assertThrows(IllegalArgumentException.class,() -> repository.findFirstByCompany(" "));
    }
}
