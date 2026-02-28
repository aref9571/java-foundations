package day03;

import javax.sound.midi.Track;

public class ApplicationRules {
    public boolean isStrongCandidate(int yearsOfExperience , boolean knowJava , boolean knowSpringBoot){
        return (yearsOfExperience >= 1 && knowJava && knowSpringBoot) || (yearsOfExperience >= 2 && knowJava);
    }
    public boolean shouldSendFollowUpEmail(boolean alreadySent , int daysSinceLastContact){
        if (alreadySent){
            return false;
        }
        else {
            return daysSinceLastContact >= 7;
        }
    }
    public boolean isValidInterviewSlot(boolean isWeekend , int hourOfDay){
        if (isWeekend){
            return false;
        }
        return 9 <= hourOfDay && hourOfDay <= 18;
    }

}
