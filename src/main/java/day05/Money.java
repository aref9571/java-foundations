package day05;

import java.util.Objects;

public final class Money {

    private final long amountInCents;
    private final String currency;

    public Money(long amountInCents, String currency) {
        validateCurrency(currency);
        this.amountInCents = amountInCents;
        this.currency = currency;
    }

    public long amountInCents() {
        return amountInCents;
    }

    public String currency() {
        return currency;
    }

    public Money add(Money other) {
        requireNonNull(other);
        requireSameCurrency(other);
        long resultAmount = this.amountInCents + other.amountInCents;
        return new Money(resultAmount, this.currency);
    }

    public Money subtract(Money other) {
        requireNonNull(other);
        requireSameCurrency(other);
        long resultAmount = this.amountInCents - other.amountInCents;
        return new Money(resultAmount, this.currency);
    }

    private static void validateCurrency(String currency) {
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("currency must be non-blank");
        }
    }

    private static void requireNonNull(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("other money must not be null");
        }
    }

    private void requireSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                    "Currencies must match: " + this.currency + " vs " + other.currency
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money)) {
            return false;
        }
        Money that = (Money) o;
        return amountInCents == that.amountInCents
                && currency.equals(that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountInCents, currency);
    }

    @Override
    public String toString() {
        return "Money{amountInCents=" + amountInCents +
                ", currency='" + currency + '\'' +
                '}';
    }
}
