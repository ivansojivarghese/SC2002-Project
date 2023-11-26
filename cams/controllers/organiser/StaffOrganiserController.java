package cams.controllers.organiser;

import cams.camp.Camp;
import cams.camp.CampRepository;
import cams.controllers.participant.ParticipationController;
import cams.controllers.participant.StudentParticipationController;
import cams.database.UnifiedCampRepository;
import cams.database.UnifiedUserRepository;
import cams.users.UserRepository;
import cams.camp.CampDetails;
import cams.users.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Concrete class responsible for implementing the functionalities defined in OrganiserController.
 * Handles the behaviours related to a Staff member camp organiser,
 * such as creating camps, editing camps, assigning students and deleting camps
 */
public class StaffOrganiserController implements OrganiserController, Serializable {
    private final CampRepository campRepo;
    /**
     * Constructor instantiates the class
     */
    public StaffOrganiserController() {
        this.campRepo = UnifiedCampRepository.getInstance();
    }

    /**
     * Gets details of the camp
     * @param campName The name of the camp whose details are being requested.
     * @return Returns details of the specified camp as a {@link CampDetails} object
     */
    public CampDetails getCampDetails(String campName){
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

    /**
     * Creates camp using the camp details received by its parameter
     * @param details The {@link CampDetails} object containing information about the new camp.
     */
    @Override
    public void createCamp(CampDetails details) {
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

    /**
     * Edits an existing camp owned by the user.
     * @param campName The name of the camp to be edited.
     * @param details  The new {@link CampDetails} to be applied to the camp.
     */
    @Override
    public void editCamp(String campName, CampDetails details) {
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

    /**
     * Deletes an existing camp owned by the user.
     * @param campName The name of the camp to be deleted.
     */
    @Override
    public void deleteCamp(String campName) {
        UserRepository userRepo = UnifiedUserRepository.getInstance();

        Camp camp = campRepo.retrieveCamp(campName);
        User creator = userRepo.retrieveUser(camp.getInCharge());

        creator.removeCamp(campName);
        campRepo.deleteCamp(campName);
    }

    /**
     * Assigns a user to a specified camp as a participationController.
     * @param UserID   The user ID of the user to be assigned to the camp.
     * @param campName The name of the camp to which the user is being assigned.
     */
    @Override
    public void assignCamp(String UserID, String campName) {
        //TODO implement camp assignment
    	
    	UserRepository userRepo = UnifiedUserRepository.getInstance();
    	ParticipationController participationController = StudentParticipationController.getInstance();

        participationController.register(userRepo.retrieveUser(UserID), campName);
    }

    /**
     * Checks if the specified camp name is unique in the database.
     * @param campName The name of the camp to check.
     * @return true if unique, otherwise false
     */
    public boolean isCampNameUnique(String campName) {
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        return !campRepo.Exists(campName);
    }

    /**
     * Gets the total number of attendees currently registered for the specified camp.
     * @param campName The name of the camp.
     * @return int value containing the number of registered participants in the camp
     */
    public int getNumAttendees(String campName){
        CampRepository campRepo = UnifiedCampRepository.getInstance();
        Camp camp = campRepo.retrieveCamp(campName);
        return camp.getNumAttendees();
    }

    /**
     * Displays all available camps.
     *
     * @return The number of camps displayed.
     */
    public int viewAllCamps(User user) {
        CampRepository repo = UnifiedCampRepository.getInstance();
        Set<Camp> allCamps = new HashSet<>(repo.allCamps());

        if(allCamps.isEmpty()) {
            System.out.println("No camps available.");
            return 0;
        }
        for(Camp c : allCamps){
            System.out.println("_________________________________");
            c.display();
        }
        return allCamps.size();
    }
}
