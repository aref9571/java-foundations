import day05.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void moniesWithSameAmountAndCurrencyAreEqual() {
        Money first = new Money(1000L, "EUR");
        Money second = new Money(1000L, "EUR");

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void moniesWithDifferentAmountAreNotEqual() {
        Money first = new Money(1000L, "EUR");
        Money second = new Money(2000L, "EUR");

        assertNotEquals(first, second);
    }

    @Test
    void moniesWithDifferentCurrencyAreNotEqual() {
        Money first = new Money(1000L, "EUR");
        Money second = new Money(1000L, "USD");

        assertNotEquals(first, second);
    }
    @Test
    void addWithSameCurrencySumsAmounts(){
        Money first = new Money(100L , "USD");
        Money second = new Money(200L , "USD");
        Money result = first.add(second);
        assertEquals(300L , result.amountInCents());
        assertEquals("USD" , result.currency());
    }
}
