package day02;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringToolkitTest {
    @Test
    void isBlank_null_isTrue() {
        String a = null;
        boolean result = StringToolkit.isBlank(a);
        assertTrue(result);
    }

    @Test
    void isBlank_spaces_isTrue() {
        String a = " ";
        boolean result = StringToolkit.isBlank(a);
        assertTrue(result);
    }

    @Test
    void isBlank_nonEmpty_isFalse() {

        String s = "a";
        boolean result = StringToolkit.isBlank(s);
        assertFalse(result);
    }

    @Test
    void requireNonBlank_null_throwsAndMessageContainsFieldName(){
        String a = null;
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class ,
                () -> StringToolkit.requireNoneBlank(a , "username")
        );
        assertEquals("username must not be blank" , ex.getMessage());
    }
    @Test
    void requireNonBlank_whitespace_throws() {

        String s = "   ";
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> StringToolkit.requireNoneBlank(s, "username")
        );
        assertEquals("username must not be blank", ex.getMessage());
    }
    @Test
    void requireNonBlank_valid_returnsOriginalString() {
        String s = "Aref";
        String result = StringToolkit.requireNoneBlank(s , "name");
        assertSame("Aref" , result);

    } @Test
    void normalizeEmail_trimsAndLowercases() {
        String email = " TeSt@Example.com ";
        String result = StringToolkit.normalizeEmail(email);
        assertEquals("test@example.com", result);
    }

    @Test
    void normalizeEmail_blank_throws() {
        String email = "   ";
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> StringToolkit.normalizeEmail(email)
        );
        assertEquals("email must not be blank", ex.getMessage());
    }

}
