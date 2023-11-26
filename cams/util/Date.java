package cams.util;

import cams.camp.Camp;
import cams.users.User;
import cams.database.UnifiedCampRepository;

import java.time.LocalDate;

//Utility Functions related to Date

 /**
 * The class Date
 */ 
public class Date {

/** 
 *
 * Do dates clash
 *
 * @param start1  the start1. 
 * @param end1  the end1. 
 * @param start2  the start2. 
 * @param end2  the end2. 
 * @return boolean
 */
    public static boolean doDatesClash(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) { 

        return !start1.isAfter(end2) && !end1.isBefore(start2); //returns true if there is an overlap (clash) and false otherwise.
    }

/** 
 *
 * Check clashes
 *
 * @param user  the user. 
 * @param camp  the camp. 
 * @return boolean
 */
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
