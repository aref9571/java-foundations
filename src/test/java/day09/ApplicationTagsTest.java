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
    void addTag_thenRetrievedTagsContainIt(){
        tags.addTags("Google" , "remote");
        Set<String> googleTag = tags.getTags("Google");
        assertTrue(googleTag.contains("remote") , "After adding 'remote' to Google , getTags should contain it");
    }

    @Test
    void addSameTagTwice_onlyStoredOnce(){
        tags.addTags("Google" , "remote");
        tags.addTags("Google" , "remote");
        Set<String> googleTag = tags.getTags("Google");
        assertEquals(1 , googleTag.size());
    }

    @Test
    void getTags_unknownCompany_returnsEmptySetNotNull(){
        Set<String> result = tags.getTags("Unknown");
        assertNotNull(result , "getTags must not return null");
        assertTrue(result.isEmpty() , "Unknown should return an empty set");
    }
    @Test
    void hasTag_isCaseInsensitive(){
        tags.addTags("Meta" , "REMOTE");
        assertTrue(tags.hasTag("Meta","remote"));
        assertTrue(tags.hasTag("Meta" , "Remote"));
        assertTrue(tags.hasTag("Meta" , "REMOTE"));
    }
    @Test
    void viewAll_modifyingReturnedMapDoesNotAffectInternalState(){
        tags.addTags("Amazon" , "Remote");
        Map<String , Set<String>> snapShot = tags.viewAll();
        snapShot.put("FakeCompany" , Set.of("FakeTag"));
        Map<String , Set<String>> secondSnapshot = tags.viewAll();
        assertTrue(snapShot.containsKey("FakeCompany"));
        assertFalse(secondSnapshot.containsKey("FakeCompany"));
    }

    @Test
    void addTag_blankCompany_throwsException(){
        assertThrows(IllegalArgumentException.class,() -> tags.addTags("" , "remote"));
    }

    @Test
    void addTag_blankTag_throwsException(){
        assertThrows(IllegalArgumentException.class, () -> tags.addTags("Meta" , " "));
    }



}
