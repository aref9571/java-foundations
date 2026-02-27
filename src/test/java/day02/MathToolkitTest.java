package day02;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MathToolkitTest {

    @Test
    void sum_withPositives(){
        int a = 2;
        int b = 3;

        int result = MathToolkit.sum(a , b);
        assertEquals(5 , result);
    }

    @Test
    void sum_withNegatives(){
        int a = -2;
        int b = -3;
        int result = MathToolkit.sum(a, b);
        assertEquals(-5 , result);
    }

    @Test
    void sum_withZero(){
        int a = 0;
        int b = 3;
        int result = MathToolkit.sum(a, b);
        assertEquals(3 , result);
    }
    @Test
    void max_withDistinctValues(){
        int a = 1;
        int b = 9;
        int c = 3;
        int result = MathToolkit.max(a, b , c);
        assertEquals(9 , result);
    }
    @Test
    void max_withTies() {
        int a = 3;
        int b = 0;
        int c = 3;
        int result = MathToolkit.max(a, b, c);
        assertEquals(3, result);
    }
    @Test
    void isEven_positiveEven(){
        int a = 8;
        boolean result = MathToolkit.isEven(a);
        assertTrue(result);
    }
    @Test
    void isEven_positiveOdd() {
        int n = 7;
        boolean result = MathToolkit.isEven(n);
        assertFalse(result);
    }
    @Test
    void isEven_negativeEven() {
        int n = -2;
        boolean result = MathToolkit.isEven(n);
        assertTrue(result);
    }
    @Test
    void isEven_negativeOdd() {
        int n = -3;
        boolean result = MathToolkit.isEven(n);
        assertFalse(result);
    }
    @Test
    void clamp_insideRange_returnSameValue(){
        int value = 5;
        int result = MathToolkit.clamp(value , 1 , 10);
        assertEquals(5 , result);
    }
    @Test
    void clamp_belowRange_returnsMin() {
        int value = -1;
        int result = MathToolkit.clamp(value, 0, 10);
        assertEquals(0, result);
    }
    @Test
    void clamp_aboveRange_returnsMax() {
        int value = 99;
        int result = MathToolkit.clamp(value, 0, 10);
        assertEquals(10, result);
    }
    @Test
    void clamp_invalidRange_throwsIllegalArgumentException(){
        int value = 10;
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> MathToolkit.clamp(value , 10 , 1)
        );

        assertEquals("min must be <= max" , ex.getMessage());
    }






}
