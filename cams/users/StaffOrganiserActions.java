package cams.users;

import cams.Camp;
import cams.database.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.database.UnifiedUserRepository;
import cams.database.UserRepository;

public class StaffOrganiserActions implements Organiser{
    public StaffOrganiserActions() {}

    public CampDetails getCampDetails(String campName){
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        UnifiedUserRepository userRepo = UnifiedUserRepository.getInstance();

        CampDetails details = new CampDetails();
        Camp camp = campRepo.retrieveCamp(campName);

        details.setCampName(campName);
        details.setInCharge(camp.getInCharge());
        details.setVisibility(camp.getVisible());
        details.setDescription(camp.getDescription());
        details.setCommitteeSlots(camp.getCommitteeSlots());
        details.setAttendeeSlots(camp.getAttendeeSlots());
        details.setLocation(camp.getLocation());
        details.setFacultyRestriction(camp.getFacultyRestriction());
        details.setEndDate(camp.getEndDate());
        details.setStartDate(camp.getStartDate());
        details.setClosingDate(camp.getClosingDate());

        return details;
    }

    @Override
    public void createCamp(CampDetails details) {
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        UnifiedUserRepository userRepo = UnifiedUserRepository.getInstance();

        User creator = userRepo.retrieveUser(details.getInCharge());

        //Use a builder for better readability
        Camp newCamp = new Camp.Builder()
                .campName(details.getCampName())
                .startDate(details.getStartDate())
                .endDate(details.getEndDate())
                .closingDate(details.getClosingDate())
                .location(details.getLocation())
                .attendeeSlots(details.getAttendeeSlots())
                .committeeSlots(details.getCommitteeSlots())
                .description(details.getDescription())
                .inCharge(details.getInCharge())
                .facultyRestriction(details.getFacultyRestriction())
                .visible(details.isVisible())
                .build();

        campRepo.addCamp(newCamp);
        creator.addCamp(newCamp);
    }

    @Override
    public void editCamp(String campName, CampDetails details) {
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        UnifiedUserRepository userRepo = UnifiedUserRepository.getInstance();

        Camp camp = campRepo.retrieveCamp(campName);
        if (camp == null) {
            // Handle the case where camp is not found
            throw new IllegalArgumentException("Camp not found with name: " + campName);
        }
        //Special procedure if changing name
        if(!campName.equalsIgnoreCase(details.getCampName())) {
            String newName = details.getCampName();
            if(this.isCampNameUnique(newName)) {
                //Remove the old entry
                campRepo.deleteCamp(campName);
                //Change name
                camp.setCampName(newName);
                campRepo.addCamp(camp);
            }
        }
        camp.updateDetails(details);
    }

    @Override
    public void deleteCamp(String campName) {
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        UserRepository userRepo = UnifiedUserRepository.getInstance();

        Camp camp = campRepo.retrieveCamp(campName);
        User creator = userRepo.retrieveUser(camp.getInCharge());

        creator.removeCamp(campName);
        campRepo.deleteCamp(campName);
    }

    @Override
    public void assignCamp(String UserID, String campName) {
        //TODO implement camp assignment
    }

    public boolean isCampNameUnique(String campName) {
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        UnifiedUserRepository userRepo = UnifiedUserRepository.getInstance();

        return !campRepo.Exists(campName);
    }

    public int getNumAttendees(String campName){
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        UnifiedUserRepository userRepo = UnifiedUserRepository.getInstance();

        Camp camp = campRepo.retrieveCamp(campName);
        User creator = userRepo.retrieveUser(camp.getInCharge());
        return camp.getNumAttendees();
    }
}
