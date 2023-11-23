package cams.users;

import cams.Camp;
import cams.database.CampRepository;
import cams.database.UnifiedCampRepository;
import cams.database.UnifiedUserRepository;
import cams.database.UserRepository;

public class StaffOrganiserActions implements Organiser{
    //Dependency injection of interface with camp repository and user repository functions
    private CampRepository campRepo;
    private UserRepository userRepo;

    public StaffOrganiserActions() {
        //Instantiate interface dependencies
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        UnifiedUserRepository userRepo = UnifiedUserRepository.getInstance();
    }
    @Override
    public void createCamp(CampDetails details) {
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
                .visible(details.isVisibility())
                .build();

        campRepo.addCamp(newCamp);
        creator.addCamp(newCamp);
    }

    @Override
    public void editCamp(String campName, CampDetails details) {

    }

    @Override
    public void deleteCamp(String campName) {
        Camp camp = campRepo.retrieveCamp(campName);
        User creator = userRepo.retrieveUser(camp.getInCharge());
        creator.removeCamp(camp.getCampName());
        campRepo.deleteCamp(camp.getCampName());
    }

    @Override
    public void assignCamp(String UserID, String campName) {

    }

    public boolean isCampNameUnique(String campName) {
        return !campRepo.Exists(campName);
    }
}
