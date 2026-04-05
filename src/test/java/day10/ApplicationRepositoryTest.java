package day10;
import day05.ApplicationStatus;
import day05.JobApplication;
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
        Money salary = new Money(1000L , "EUR");
        LocalDate date = LocalDate.of(2026 , 4 , 5);
        JobApplication app1 = new JobApplication("Meta" , "Backend" , ApplicationStatus.APPLIED , date , salary , UUID.randomUUID());
        JobApplication app2 = new JobApplication("Google" , "Engineer" , ApplicationStatus.INTERVIEWING , date , salary , UUID.randomUUID());
        JobApplication app3 = new JobApplication("Amazon" , "Backend" , ApplicationStatus.REJECTED , date , salary , UUID.randomUUID());
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
        Money salary = new Money(1000L, "EUR");
        LocalDate date = LocalDate.of(2026, 4, 5);
        repository.add(new JobApplication("Meta", "Backend", ApplicationStatus.APPLIED, date, salary, UUID.randomUUID()));

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
        Money salary = new Money(1000L , "EUR");
        LocalDate date = LocalDate.of(2026 , 4 , 5);
        JobApplication app1 = new JobApplication("Meta" , "Backend" , ApplicationStatus.APPLIED , date , salary , UUID.randomUUID());
        JobApplication app2 = new JobApplication("Amazon" , "Engineer" , ApplicationStatus.APPLIED , date , salary , UUID.randomUUID());
        JobApplication app3 = new JobApplication("Amazon" , "Backend" , ApplicationStatus.REJECTED , date , salary , UUID.randomUUID());
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
        Money salary = new Money(1000L , "EUR");
        LocalDate date = LocalDate.of(2026 , 4 , 5);
        JobApplication app1 = new JobApplication("Meta" , "Backend" , ApplicationStatus.APPLIED , date , salary , UUID.randomUUID());
        JobApplication app2 = new JobApplication("Amazon" , "Engineer" , ApplicationStatus.INTERVIEWING , date , salary , UUID.randomUUID());
        JobApplication app3 = new JobApplication("Amazon" , "Backend" , ApplicationStatus.REJECTED , date , salary , UUID.randomUUID());
        repository.add(app1);
        repository.add(app2);
        repository.add(app3);

        long result = repository.countActive();
        assertEquals(2L , result);
    }
}
