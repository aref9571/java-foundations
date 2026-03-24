package day09;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.util.Map;
import java.util.Set;
class ApplicationTagsTest {
    private ApplicationTags tags;

    @BeforeEach
    void setUp(){
        tags = new ApplicationTags();
    }
    @Test
    void addTag_thenRetrievedTagsContainIt() {
        tags.addTags("Google", "remote");

        Set<String> googleTags = tags.getTags("Google");
        assertTrue(googleTags.contains("remote"),
                "After adding 'remote' to Google, getTags should contain it");
    }
    @Test
    void addSameTagTwice_onlyStoredOnce(){
        tags.addTags("Google" , "Remote");
        tags.addTags("Google" , "Remote");
        Set<String> googleTags = tags.getTags("Google");
        assertEquals(1,googleTags.size());
    }


}
