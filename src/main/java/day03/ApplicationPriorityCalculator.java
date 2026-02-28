package day03;
import java.util.Locale;

public class ApplicationPriorityCalculator {
    public int priorityFromSource(String source) {
        if (source == null) {
            return 0;
        }
        String normalized = source.toLowerCase(Locale.ROOT);
        return switch (normalized) {
            case "referral" -> 3;
            case "career_fair", "linkedin" -> 2;
            case "cold_email" -> 1;
            default -> 0;
        };
    }

    public String describePriority(int priority){
        return switch (priority){
            case 3 -> "HIGH";
            case 2 -> "MEDIUM";
            case 1 -> "LOW";
            default -> "IGNORE";
        };
    }

    public int overallPriority(String source, boolean isStrongCandidate) {
        int basePriority = priorityFromSource(source);

        if (isStrongCandidate && basePriority < 3) {
            return basePriority + 1;
        }

        return basePriority;
    }

}