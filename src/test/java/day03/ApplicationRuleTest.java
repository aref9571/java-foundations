package day03;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationRuleTest {
    @Test
    void strongWhenOneYearJavaAndSpring(){
        ApplicationRules rules = new ApplicationRules();
        boolean result = rules.isStrongCandidate(1,true,true);
        assertTrue(result);
    }
    @Test
    void strongWhenTwoYearsJava(){
        ApplicationRules rules = new ApplicationRules();
        boolean result = rules.isStrongCandidate(2,true,false);
        assertTrue(result);
    }
    @Test
    void strongWhenJavaAndSpring(){
        ApplicationRules rules = new ApplicationRules();
        boolean result = rules.isStrongCandidate(0,true,true);
        assertFalse(result);
    }

    @Test
    void shouldSendFollowUpEmail_alreadySentTrue_returnsFalse() {

        ApplicationRules rules = new ApplicationRules();
        boolean result = rules.shouldSendFollowUpEmail(true, 100);
        assertFalse(result);
    }

    @Test
    void shouldSendFollowUpEmail_notSentAndTooSoon_returnsFalse() {
        ApplicationRules rules = new ApplicationRules();
        boolean result = rules.shouldSendFollowUpEmail(false, 3);
        assertFalse(result);
    }

    @Test
    void shouldSendFollowUpEmail_notSentAndSevenDays_returnsTrue() {

        ApplicationRules rules = new ApplicationRules();
        boolean result = rules.shouldSendFollowUpEmail(false, 7);
        assertTrue(result);
    }

    @Test
    void isValidInterviewSlot_weekendHour10_returnsFalse() {

        ApplicationRules rules = new ApplicationRules();
        boolean result = rules.isValidInterviewSlot(true, 10);
        assertFalse(result);
    }

    @Test
    void isValidInterviewSlot_weekdayHour8_returnsFalse() {

        ApplicationRules rules = new ApplicationRules();
        boolean result = rules.isValidInterviewSlot(false, 8);
        assertFalse(result);
    }

    @Test
    void isValidInterviewSlot_weekdayHour9_returnsTrue() {

        ApplicationRules rules = new ApplicationRules();
        boolean result = rules.isValidInterviewSlot(false, 9);
        assertTrue(result);
    }

    @Test
    void isValidInterviewSlot_weekdayHour18_returnsTrue() {

        ApplicationRules rules = new ApplicationRules();
        boolean result = rules.isValidInterviewSlot(false, 18);
        assertTrue(result);
    }

    @Test
    void isValidInterviewSlot_weekdayHour19_returnsFalse() {

        ApplicationRules rules = new ApplicationRules();
        boolean result = rules.isValidInterviewSlot(false, 19);
        assertFalse(result);
    }



}
