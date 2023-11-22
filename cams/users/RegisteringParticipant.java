package cams.users;

import cams.Camp;
import cams.database.UnifiedCampRepository;
import cams.util.Date;

public class RegisteringParticipant implements ParticipantActions {
    @Override
    public void manageRegistration(User user, String campName) {
        //TODO prevent participants for registering for camps that are closed OR over
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        if (repo.getSize() == 0) {
            System.out.println("No camps exist.");
            return;
        }
        Camp selectedCamp = repo.retrieveCamp(campName);

        boolean Registered = user.getMyCamps().contains(selectedCamp.getCampName()); // check if user has registered for camp already
        boolean datesClash = Date.checkClashes(user, selectedCamp);
        // check for clashes in dates with other camps
        boolean availableSlot = selectedCamp.getRemainingAttendeeSlots() > 0;

        //FAILURE outcomes
        if (Registered) {
            System.out.println("Camp is already registered.");
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
    }

}
