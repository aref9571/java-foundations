package day03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationPriorityCalculatorTest {

    @Test
    void priorityFromSource_referral_returns3() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        int result = calc.priorityFromSource("referral");
        assertEquals(3, result);
    }

    @Test
    void priorityFromSource_uppercaseReferral_returns3() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        int result = calc.priorityFromSource("REFERRAL");
        assertEquals(3, result);
    }

    @Test
    void priorityFromSource_linkedin_returns2() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        int result = calc.priorityFromSource("linkedin");
        assertEquals(2, result);
    }

    @Test
    void priorityFromSource_unknown_returns0() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        int result = calc.priorityFromSource("unknown");
        assertEquals(0, result);
    }

    @Test
    void describePriority_3_returnsHigh() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        String result = calc.describePriority(3);
        assertEquals("HIGH", result);
    }

    @Test
    void describePriority_2_returnsMedium() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        String result = calc.describePriority(2);
        assertEquals("MEDIUM", result);
    }

    @Test
    void describePriority_1_returnsLow() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        String result = calc.describePriority(1);
        assertEquals("LOW", result);
    }

    @Test
    void describePriority_other_returnsIgnore() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        String result = calc.describePriority(42);
        assertEquals("IGNORE", result);
    }

    @Test
    void overallPriority_referralStrong_returns3() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        int result = calc.overallPriority("referral", true);
        assertEquals(3, result);
    }

    @Test
    void overallPriority_linkedinStrong_returns3() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        int result = calc.overallPriority("linkedin", true);
        assertEquals(3, result);
    }

    @Test
    void overallPriority_coldEmailNotStrong_returns1() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        int result = calc.overallPriority("cold_email", false);
        assertEquals(1, result);
    }

    @Test
    void overallPriority_unknownStrong_returns1() {
        ApplicationPriorityCalculator calc = new ApplicationPriorityCalculator();
        int result = calc.overallPriority("unknown", true);
        assertEquals(1, result);
    }
}
