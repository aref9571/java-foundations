package day09;
import day05.ApplicationStatus;
import day05.JobApplication;
import day05.JobApplicationBuilder;
import day05.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationIndexTest {
    private ApplicationIndex index;
    @BeforeEach
    void setUp(){
        index = new ApplicationIndex();
    }

    @Test
    void  add_increasesAllSize() {
        assertEquals(0, index.all().size() , "Initial size should be zero");
        index.add(JobApplicationBuilder.aDefaultApplication().build());
        assertEquals(1 , index.all().size(), "After one add , size should be 1");
        index.add(JobApplicationBuilder.aDefaultApplication().build());
        assertEquals(2 , index.all().size() , "After second add , the size should be 2");
    }

    @Test
    void addNullApplication(){
        assertThrows(IllegalArgumentException.class,() ->
                index.add(new JobApplication(
                        null , null , null ,null ,null, null)));
    }

    @Test
    void findByCompany_returnsCorrectSubset(){
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().withCompany("Meta").build();
        JobApplication app2 =  JobApplicationBuilder.aDefaultApplication().withCompany("Meta").build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication().build();

        index.add(app1);
        index.add(app2);
        index.add(app3);

        List<JobApplication> metaApplications = index.findByCompany("Meta");
        assertEquals(2 , metaApplications.size());
        assertTrue(metaApplications.contains(app1));
        assertTrue(metaApplications.contains(app2));


    }

    @Test
    void findByCompany_unknown_returnsEmptyList(){
        JobApplication app = JobApplicationBuilder.aDefaultApplication().build();

        index.add(app);

        List<JobApplication> googleApplications = index.findByCompany("Google");
        assertNotNull(googleApplications);
        assertTrue(googleApplications.isEmpty());
    }

    @Test
    void countByStatus_returnsCorrectCounts(){
        JobApplication app1 = JobApplicationBuilder.aDefaultApplication().build();
        JobApplication app2 =  JobApplicationBuilder.aDefaultApplication().build();
        JobApplication app3 = JobApplicationBuilder.aDefaultApplication().withStatus(ApplicationStatus.INTERVIEWING).build();
        index.add(app1);
        index.add(app2);
        index.add(app3);

        Map<ApplicationStatus , Long> counts = index.countByStatus();
        assertEquals(2L , counts.get(ApplicationStatus.APPLIED));
        assertEquals(1L , counts.get(ApplicationStatus.INTERVIEWING));
        assertNull(counts.get(ApplicationStatus.REJECTED));

    }

}
