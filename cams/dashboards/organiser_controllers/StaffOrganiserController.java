package cams.dashboards.organiser_controllers;

import cams.camp.Camp;
import cams.camp.CampRepository;
import cams.dashboards.participant_controllers.ParticipationController;
import cams.dashboards.participant_controllers.StudentParticipationController;
import cams.database.UnifiedCampRepository;
import cams.database.UnifiedUserRepository;
import cams.users.UserRepository;
import cams.util.UserInput;
import cams.camp.CampDetails;
import cams.users.User;

import java.io.Serializable;
import java.util.ArrayList;
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
    	ParticipationController participationController = new StudentParticipationController();

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
    /*
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
    }*/
    

	public int viewAllCamps(User user) {
	    	int option;
	    	
	        CampRepository repo = UnifiedCampRepository.getInstance();
	        Set<Camp> allCamps = new HashSet<>(repo.allCamps());
	        
	        ArrayList<Object> camps = new ArrayList<Object>(); // Create an ArrayList object
	    
	    if(allCamps.isEmpty()) {
	        System.out.println("No camps available.");
	        return 0;
	    }
	    
	    System.out.println("Camp list will be shown in alphabetical order based on?");
	    System.out.println("(0) Name");
	    System.out.println("(1) Location");
	    System.out.println("(2) Description");
	    
	    option = UserInput.getIntegerInput(0, 2, "Choose a filter from above by its index:");
	    
	    // get array of camps (objects)
	    // get array of camp names (strings)
	    // insertion sort (compareTo) on camp names
	    
	    // swap objects when names are being swapped
	    // display all objects, replace the loop below
	    
	    // DEFAULT: by alphabetical order of name, compareTo
	    
	    ArrayList<String> strings = new ArrayList<String>(); // Create an ArrayList object of strings
	    
	    switch (option) {
	        case 0: // name
	        	for (Camp c: allCamps) {
	            	camps.add(c); // add camp
	            	strings.add(c.getCampName()); // add name
	            }
	        	
	        	
	    	break;
	        case 1: // location
	        	for (Camp c: allCamps) {
	            	camps.add(c); // add camp
	            	strings.add(c.getLocation()); // add location
	            }
	        	
	        	
	    	break;
	        case 2: // description
	        	for (Camp c: allCamps) {
	            	camps.add(c); // add camp
	            	strings.add(c.getDescription()); // add des.
		            }
		        	
				break;
	        }
	 
	        int n = strings.size(); 
	        
	
	        for (int j = 1; j < n; j++) {  // insertion sort
	
	        String key = strings.get(j);  // c, g
	        Object keyC = camps.get(j); // c, g
	        String otherkey;
	        Object otherkeyC;
	        
	        int i = j-1;  // 0, 1
	        while ( (i > -1) && ( strings.get(i).compareToIgnoreCase(key) > 0 ) ) {  // f vs c, f vs g
	        	String org = strings.get(i); // f
	        	Object orgC = camps.get(i); // f
	        	String nw = strings.get(i + 1); // c
	        	Object nwC = camps.get(i + 1); // c
	            
	            strings.set(i + 1, org);
	            camps.set(i + 1, orgC);
	            
	            i--;  
	        }  
	        otherkey = strings.get(i + 1); // f, g
	        otherkeyC = camps.get(i + 1); // f, g
	        
	        strings.set(i + 1, key);
	        camps.set(i + 1, keyC);
	        
	    }  
	
	 
	    for (Object c: camps) {
	    	System.out.println("_________________________________");
	        ((Camp) c).display();
	    }
	  
	    
	    return allCamps.size();
	}
}
