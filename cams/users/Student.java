package cams.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cams.Camp;
import cams.post_types.*;
import cams.database.UnifiedCampRepository;
import cams.util.Faculty;

public class Student extends User implements Participant { // student class
    private String myCommittee;

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

    /*
    public void setPassword(int identity, String password) {
    	studentArr[identity].password = password;
    }
 
    // Student class constructor
    Student(String name, String userID, String faculty, String password, int identity)
    {
        this.name = name;
        this.userID = userID;
        this.faculty = faculty;
        if (password == null) {        	
        	this.password = "password";
        } else {        	
        	setPassword(identity, password);
        }
    }

    public List<Post> getMyEnquiries(){
        return this.myEnquiries;
    }*/

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
        int index = super.displayMyCamps();
        if(index == 0)
            System.out.println("No camps registered.");
        return index;
    }

    //TODO implement this method to return all camps available to user
    public void viewAllCamps() {
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for(Camp c : repo.filterCampByFaculty(this.getFaculty())){
            System.out.println("_________________________________");
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
        ParticipantActions action = new RegistratingParticipant();
        action.manageRegistration(this, campName);
    }


    //only staff can edit camps, students cannot edit camps. shld never implement/create methods that are not used.
    /*
	@Override
	public void editMyCamps() {
		// TODO Auto-generated method stub
		
	}*/
}
