package cams.users;

import java.util.*;

import cams.Camp;
import cams.dashboards.DashboardState;
import cams.dashboards.StudentMenuState;
import cams.post_types.*;
import cams.database.UnifiedCampRepository;
import cams.util.Faculty;

public class Student extends User implements Participant { // student class
    private String myCommittee;

    public DashboardState getMenuState() {
        return new StudentMenuState();
    }

    //returns false if student's myCommmittee variable is NA
    public boolean isCommittee() {
        return !myCommittee.equalsIgnoreCase("NA");
    }

    public void setCommittee(String committee) {
        myCommittee = committee;
    }

    public String getCommittee(){
        return this.myCommittee;
    }
    
    public Student(String name, String userID, Faculty faculty) {
    	super(name, userID, faculty);
        this.setCommittee("NA");
    }

    public List<Post> getEnquiries() {
        List<Post> myEnquiries = new ArrayList<>();
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for (String campName : this.getMyCamps()) {
            Camp camp = repo.retrieveCamp(campName);
            for (Post post : camp.getEnquiries()) {
                if (Objects.equals(post.getFirstMessage().getPostedBy(), this.getUserID())) {
                    myEnquiries.add(post);
                }
            }
        }
        return myEnquiries;
    }

    //Refinement of displayMyCamps() method in User superclass
    public int displayMyCamps(){
        System.out.println("My registered camps: ");
        int index = 0;
        try {
            index = super.displayMyCamps();
        }
        catch (NullPointerException e){
            System.out.println("No camps registered.");
        }
        if(index == 0)
            System.out.println("No camps registered.");
        return index;
    }

    //Displays camp information for all camps visible and available to the user
    public void viewAllCamps() {
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();

        // Get camps from both categories in a hashset of unique values to avoid duplicates
        Set<Camp> allCamps = new HashSet<>();
        allCamps.addAll(repo.filterCampByFaculty(Faculty.ALL));
        allCamps.addAll(repo.filterCampByFaculty(this.getFaculty()));

        if (allCamps.isEmpty()) {
            System.out.println("No camps available.");
            return;
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
    }

    @Override
    public List<Post> getSuggestions() {
        List<Post> mySuggestions = new ArrayList<>();
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for (String campName : this.getMyCamps()) {
            Camp camp = repo.retrieveCamp(campName);
            for (Post post : camp.getSuggestions()) {
                if (Objects.equals(post.getFirstMessage().getPostedBy(), this.getUserID())) {
                    mySuggestions.add(post);
                }
            }
        }
        return mySuggestions;
    }

    //IMPLEMENT Participant
    public void deregister(String campName){
        ParticipantActions action = new DeregisteringParticipant();
        if(!campName.equalsIgnoreCase(this.getCommittee())) {
            action.manageRegistration(this, campName);
        }
        else
            System.out.println("Camp committee members cannot withdraw from their camp.");
    }

    public void register(String campName){
        ParticipantActions action = new RegisteringParticipant();
        action.manageRegistration(this, campName);
    }


    //only staff can edit camps, students cannot edit camps. shld never implement/create methods that are not used.
    /*
	@Override
	public void editMyCamps() {
		// TODO Auto-generated method stub
		
	}*/
}
