package day05;

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

    @Test
    void arithmeticOverflowThrowsInsteadOfWrapping(){
        Money maximum = new Money(Long.MAX_VALUE, "USD");
        Money oneCent = new Money(1, "USD");

        assertThrows(ArithmeticException.class, () -> maximum.add(oneCent));
    }

    @Test
    void subtractWithSameCurrencySubtractsAmounts() {
        Money first = new Money(500L, "EUR");
        Money second = new Money(200L, "EUR");

        Money result = first.subtract(second);

        assertEquals(new Money(300L, "EUR"), result);
    }

    @Test
    void addWithDifferentCurrencyThrows() {
        Money euros = new Money(100L, "EUR");
        Money dollars = new Money(100L, "USD");

        assertThrows(IllegalArgumentException.class, () -> euros.add(dollars));
    }

    @Test
    void currencyIsNormalizedToUpperCaseIsoCode() {
        Money money = new Money(100L, " eur ");

        assertEquals("EUR", money.currency());
        assertEquals(new Money(100L, "EUR"), money);
    }

    @Test
    void unknownCurrencyCodeThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Money(100L, "euro"));
        assertThrows(IllegalArgumentException.class, () -> new Money(100L, "XYZ"));
    }

    @Test
    void blankOrNullCurrencyThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Money(100L, " "));
        assertThrows(IllegalArgumentException.class, () -> new Money(100L, null));
    }
}
