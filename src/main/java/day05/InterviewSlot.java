package day05;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public final class InterviewSlot {

    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final boolean remote;

    public InterviewSlot(LocalDate date,
                         LocalTime startTime,
                         LocalTime endTime,
                         boolean remote) {

        validateDate(date);
        validateTimes(startTime, endTime);
        validateBusinessHours(startTime, endTime);

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remote = remote;
    }

    public LocalDate date() {
        return date;
    }

    public LocalTime startTime() {
        return startTime;
    }

    public LocalTime endTime() {
        return endTime;
    }

    public boolean remote() {
        return remote;
    }

    public Duration duration() {
        return Duration.between(startTime, endTime);
    }

    public boolean overlaps(InterviewSlot other) {
        if (other == null) {
            return false;
        }
        if (!this.date.equals(other.date)) {
            return false;
        }
        return this.startTime.isBefore(other.endTime)
                && other.startTime.isBefore(this.endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterviewSlot)) return false;
        InterviewSlot that = (InterviewSlot) o;
        return remote == that.remote
                && date.equals(that.date)
                && startTime.equals(that.startTime)
                && endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, startTime, endTime, remote);
    }

    @Override
    public String toString() {
        return "InterviewSlot{" +
                "date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", remote=" + remote +
                '}';
    }

    private static void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("date must be today or in the future");
        }
    }

    private static void validateTimes(LocalTime startTime, LocalTime endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("startTime and endTime must not be null");
        }
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }
    }

    private static void validateBusinessHours(LocalTime startTime, LocalTime endTime) {
        LocalTime earliest = LocalTime.of(8, 0);
        LocalTime latest = LocalTime.of(20, 0);
        if (startTime.isBefore(earliest) || endTime.isAfter(latest)) {
            throw new IllegalArgumentException("slot must be within business hours 08:00–20:00");
        }
    }
}
