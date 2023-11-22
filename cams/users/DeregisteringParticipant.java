package cams.users;

import cams.Camp;
import cams.database.UnifiedCampRepository;

public class DeregisteringParticipant implements ParticipantActions{
    @Override
    public void manageRegistration(User user, String campName) {
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        Camp camp = repo.retrieveCamp(campName);
        if(camp == null)
        {
            System.out.println("Camp does not exist.");
            return;
        }
        if(user.getMyCamps().contains(campName)){
                camp.removeAttendee(user.getUserID());
                user.removeCamp(campName);
                System.out.println("Successfully deregistered.");
        }
        else
            System.out.println("Unable to deregister for a camp you did not register for.");
    }
}
