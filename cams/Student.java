package cams;

import java.util.List;
import java.util.Objects;

import cams.PostTypes.*;

public class Student extends User implements Participant { // student class
    public boolean isCommittee() {
        return isCommittee;
    }

    public void setCommittee(boolean committee) {
        isCommittee = committee;
    }

    private boolean isCommittee = false;

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
        List<Post> myEnquiries = null;
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

    public void displayMyCamps(){
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for(String c: this.getMyCamps()){
            Camp camp = repo.retrieveCamp(c);
            camp.display();
            System.out.println("______________");
        }
    }
    //TODO implement this method to return all camps available to user
    public List<Camp> viewAllCamps() {
        List<Camp> collection = null;
        return collection;
    }

    @Override
    public List<Post> getSuggestions() {
        List<Post> mySuggestions = null;
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
    @Override
    public void Deregister(String campName) {
        if(this.getMyCamps().contains(campName)){
            UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
            Camp camp = repo.retrieveCamp(campName);
            camp.removeAttendee(this);
            this.removeCamp(campName);
            System.out.println("Successfully deregistered.");
        }
        else
            System.out.println("Unable to deregister for a camp you did not register for.");
    }
    @Override
    public void Register(String campName) { //campIndex is the index of the camp in the list of camps available to the student user
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        if (repo.getSize() == 0) {
            System.out.println("No camps exist.");
            return;
        }
        Camp selectedCamp = repo.retrieveCamp(campName);

        boolean Registered = this.getMyCamps().contains(selectedCamp.getCampName()); // check if user has registered for camp already
        boolean datesClash = Date.checkClashes(this, selectedCamp);
        ; // check for clashes in dates with other camps
        boolean availableSlot = selectedCamp.getRemainingSlots() > 0;

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
        selectedCamp.addAttendee(this); //add attendee to camp
        this.addCamp(selectedCamp); //add camp to attendee
        System.out.println("Successfully registered.");
    }
}
