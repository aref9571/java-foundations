package day03;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationRuleTest {

    @ParameterizedTest(name = "[{index}] years={0}  java={1} , spring={2} => {3} ")
    @CsvSource({"0, false, false, false",
            "1, true,  false, false",
            "1, true,  true,  true",
            "2, true,  false, true",
            "3, true,  true,  true"})
    void isStrongCandidate_behavesAccordingToRules(int yearsOfExperience , boolean knowJava , boolean knowSpring , boolean expected){
        ApplicationRules rules = new ApplicationRules();

        boolean actual = rules.isStrongCandidate(yearsOfExperience ,knowJava , knowSpring);
        assertEquals(expected , actual);
    }

    @ParameterizedTest(name = "[{index}] alreadySent={0} , daysSinceLastContact={1} => {3}")
    @CsvSource({
            "true, 0 , false",
            "true, 10 , false",
            "false , 6 , false",
            "false , 7 , true",
            "false , 10 , true"
    })
    void shouldFollowUpEmail_behaveAccordingToRules(boolean alreadySent , int daysSinceLastContact , boolean expected){
        ApplicationRules rules = new ApplicationRules();
        boolean actual = rules.shouldSendFollowUpEmail(alreadySent , daysSinceLastContact);
        assertEquals(expected , actual);
    }

    @ParameterizedTest(name = "[{index}]  isWeekend={0} , hourOfDay={2} => {3}")
    @CsvSource({
            "true , 2 , false",
            "true , 10 , false",
            "true , 19 , false",
            "false , 5 , false",
            "false , 9 , true",
            "false , 18 , true",
            "false , 19 , false"
    })
    void isValidInterviewingSlot_behaveAccordingToRules(boolean isWeekend , int hourOfDay , boolean expected){
        ApplicationRules rules = new ApplicationRules();
        boolean actual = rules.isValidInterviewSlot(isWeekend , hourOfDay);
        assertEquals(expected , actual);
    }

}
