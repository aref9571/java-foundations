package day03;

public class ApplicationRules {
    public boolean isStrongCandidate(int yearsOfExperience , boolean knowJava , boolean knowSpringBoot){
        return hasRequiredExperienceForStrongCandidate(yearsOfExperience , knowJava , knowSpringBoot) || knowsRequiredTechnologiesForStrongCandidate(yearsOfExperience , knowJava);
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
    private boolean hasRequiredExperienceForStrongCandidate(int yearsOfExperience, boolean knowJava , boolean knowSpringBoot){
        return yearsOfExperience >= 1 && knowJava && knowSpringBoot;
    }
    private boolean knowsRequiredTechnologiesForStrongCandidate(int yearsOfExperience ,boolean knowJava){
        return yearsOfExperience >=2 && knowJava;
    }

}
