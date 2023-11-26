package cams.controllers.participant;

import cams.camp.Camp;
import cams.camp.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.users.Committable;
import cams.users.User;
import cams.util.Date;
import cams.util.Faculty;
import cams.util.UserInput;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements the {@link ParticipationController} interface, handling actions related to camp registration and deregistration.
 * This class applies the business logic to perform these actions, such as checking camp availability.
 */
public class StudentParticipationController implements ParticipationController, Serializable {
    /**
     * Deregisters a user from a specified camp.
     * <p>
     * Handles logic to ensure a user can only deregister from camps they are registered for.
     * Adds the user to a banned list of the camp preventing future registrations.
     *
     * @param user     The {@link User} who is deregistering.
     * @param campName The name of the camp from which the user is deregistering.
     */
    @Override
    //TODO prevent users from registering from deregistered camps using the banned list of each camp
    public void deregister(User user, String campName) {
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(campName);

        if(camp == null)
        {
            System.out.println("Camp does not exist.");
            return;
        }
        if(!user.getMyCamps().contains(campName)) {
            System.out.println("Unable to withdraw from a camp you did not register for.");
            return;
        }
        // If the conditional statement returns false, the user was prevented from de-registering for some reason
        // If it returns true, the camp was removed from the user's list of registered camps
        if(!user.removeCamp(campName)){
            System.out.println("You are not allowed to withdraw from this camp.");
            return;
        }
        //Remove user from camp list of attendees
        camp.removeAttendee(user.getUserID());
        
        camp.addBannedUser(user.getUserID()); // lifetime ban on user

        System.out.println("Successfully deregistered. You may not register for this Camp again.");
    }

    /**
     * Registers a user to a specified camp.
     * <p>
     * Handles logic to ensure a user can register for available camps and not for closed or over camps.
     * Additionally, checks for date clashes and allows users to join the camp committee if vacancies exist.
     *
     * @param user     The {@link User} who is registering.
     * @param campName The name of the camp to which the user is registering.
     */
    @Override
    public void register(User user, String campName) {
    	
    	String input;
    	Boolean committeeCheck;
    	
        //TODO prevent participants for registering for camps that are closed OR over
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        if (repo.getSize() == 0) {
            System.out.println("No camps exist.");
            return;
        }
        Camp selectedCamp = repo.retrieveCamp(campName);
        if(selectedCamp == null) {
            System.out.println("Camp does not exist.");
            return;
        }
        boolean Registered = user.getMyCamps().contains(selectedCamp.getCampName()); // check if user has registered for camp already
        boolean datesClash = Date.checkClashes(user, selectedCamp);
        // check for clashes in dates with other camps
        boolean availableSlot = selectedCamp.getRemainingAttendeeSlots() > 0;
        boolean isBanned = selectedCamp.isBanned(user.getUserID());

        //FAILURE outcomes
        if (Registered) {
            System.out.println("Camp is already registered.");
            return;
        }
        if(isBanned)
        {
            System.out.println("You are banned from registering for this camp.");
            return;
        }
        if (datesClash) {
            System.out.println("Unable to register due to clashes.");
            return;
        }
        if (!availableSlot) {
            System.out.println("No available slots.");
            return;
        }

        //SUCCESS outcome
        selectedCamp.addAttendee(user.getUserID()); //add attendee to camp
        user.addCamp(selectedCamp); //add camp to attendee
        
        if (selectedCamp.getCommitteeSlots() > 0) { // if committee vacancy remains
	        System.out.println("Be part of the committee? (Y: Yes, wN: No)");
	        input = UserInput.getStringInput();
	        committeeCheck = UserInput.validateInput(input);
	
	        if (Boolean.TRUE.equals(committeeCheck)) {
		        if(user instanceof Committable) {
		            // if committee slots are vacant, auto add the user
		            if (selectedCamp.addCommittee(user.getUserID()) == 1) {
		            	((Committable) user).setCommittee(campName);
		            }
		        }
	        }
        }
        
        System.out.println("Successfully registered.");
    }

    /**
     * Displays camp information for all camps visible and available to the user
     *
     * @return The number of camps displayed.
     */
    public int viewAllCamps(User user) {
        CampRepository repo = UnifiedCampRepository.getInstance();

        // Get camps from both categories in a hashset of unique values to avoid duplicates
        Set<Camp> allCamps = new HashSet<>();
        allCamps.addAll(repo.filterCampByFaculty(Faculty.ALL));
        allCamps.addAll(repo.filterCampByFaculty(user.getFaculty()));

        if (allCamps.isEmpty()) {
            System.out.println("No camps available.");
            return 0;
        }

        for (Camp c : allCamps) {
            //If camp is not visible, skip
            if (!c.getVisible()) {
                continue;
            }
            //Print divider
            System.out.println("_________________________________");
            //Display camp
            c.display();
        }
        return allCamps.size();
    }
}
