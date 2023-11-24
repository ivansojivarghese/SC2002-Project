package cams.users;

import cams.Camp;
import cams.database.UnifiedCampRepository;
import cams.util.Date;

public class ParticipantAction implements Participant{
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

    @Override
    public void register(User user, String campName) {
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
        System.out.println("Successfully registered.");
         
    	selectedCamp.addCommittee(user.getUserID()); // if committee slots are vacant, auto add the user
    	
    	Student student = (Student) user;
    	student.setCommittee(campName);
    }

}
