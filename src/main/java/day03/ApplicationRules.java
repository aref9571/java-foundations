package day03;

public class ApplicationRules {
    public boolean isStrongCandidate(int yearsOfExperience , boolean knowsJava , boolean knowsSpringBoot){
        return knowsRequiredTechnologiesForStrongCandidate(yearsOfExperience , knowsJava , knowsSpringBoot) || hasRequiredExperienceForStrongCandidate(yearsOfExperience , knowsJava);
    }
    public boolean shouldSendFollowUpEmail(boolean alreadySent , int daysSinceLastContact){
        if (alreadySent){
            return false;
        }
        else {
            return hasWaitedLongEnoughForFollowUp(daysSinceLastContact);
        }
    }
    public boolean isValidInterviewSlot(boolean isWeekend , int hourOfDay){
        if (isWeekend){
            return false;
        }
        return isBusinessHour(hourOfDay);
    }


    private boolean isBusinessHour(int hoursOfDay){
        return 9 <= hoursOfDay && hoursOfDay <= 18;
    }
    private boolean hasWaitedLongEnoughForFollowUp(int daysSinceLastContact){
        return daysSinceLastContact >=7;
    }
    private boolean knowsRequiredTechnologiesForStrongCandidate(int yearsOfExperience, boolean knowsJava , boolean knowsSpringBoot){
        return yearsOfExperience >= 1 && knowsJava && knowsSpringBoot;
    }
    private boolean hasRequiredExperienceForStrongCandidate(int yearsOfExperience ,boolean knowsJava){
        return yearsOfExperience >=2 && knowsJava;
    }

}
