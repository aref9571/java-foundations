package day12;

import static org.junit.jupiter.api.Assertions.*;

import day05.ApplicationStatus;
import org.junit.jupiter.api.*;

class PairTest {

    @Test
    void stringIntegerPairHoldsKeyAndValue(){
        Pair<String , Integer> pair = new Pair<>("Hello" , 1);

        assertEquals("Hello" , pair.getKey());
        assertEquals(1 , pair.getValue());
    }

    @Test
    void statusCountPairHoldsKeyAndValue() {
        Pair<ApplicationStatus, Long> pair = new Pair<>(ApplicationStatus.APPLIED, 5L);

        assertEquals(ApplicationStatus.APPLIED, pair.getKey());
        assertEquals(5L, pair.getValue());
    }
}
