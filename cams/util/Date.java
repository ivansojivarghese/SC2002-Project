package cams.util;

import cams.camp.Camp;
import cams.users.User;
import cams.database.UnifiedCampRepository;

import java.time.LocalDate;

//Utility Functions related to Date
public class Date {
    public static boolean doDatesClash(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !end1.isBefore(start2); //returns true if there is an overlap (clash) and false otherwise.
    }
    public static boolean checkClashes(User user, Camp camp){
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        boolean clash = false;
        for (String c: user.getMyCamps()){
            Camp registeredCamp = repo.retrieveCamp(c);
            if(Date.doDatesClash(camp.getStartDate(),camp.getEndDate(), registeredCamp.getStartDate(), registeredCamp.getEndDate())){
                System.out.println("Selected camp clashes with: " + registeredCamp.getCampName());
                clash = true;
            }
        }
        return clash;
    }
}
