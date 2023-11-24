package cams.users;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import cams.Camp;
import cams.dashboards.CommitteeMenuState;
import cams.dashboards.DashboardState;
import cams.dashboards.StudentMenuState;
import cams.database.CampRepository;
import cams.post_types.*;
import cams.database.UnifiedCampRepository;
import cams.util.Faculty;
import cams.util.SerializeUtility;

public class Student extends User implements Committable, Serializable { // student class
    @Serial
    private static final long serialVersionUID = 555657101575497102L; //crc32b Hash of "User" converted to ASCII
    private String myCommittee;

    public String getFileName() {
        return "Student_" + this.getUserID().replaceAll("\\s+", "_") + ".ser";
    }

    //Returns the correct menu depending on whether student is a committee member or not
    public DashboardState getMenuState() {
        if(this.isCommittee())
            return new CommitteeMenuState();
        else
            return new StudentMenuState();
    }

    //returns false if student's myCommittee variable is NA
    public boolean isCommittee() {
        return !myCommittee.equalsIgnoreCase("NA");
    }

    public void setCommittee(String committee) {
        myCommittee = committee;
        SerializeUtility.saveObject(this, getFileName());
    }

    public String getCommittee(){
        return this.myCommittee;
    }
    
    public Student(String name, String userID, Faculty faculty) {
    	super(name, userID, faculty);
        this.setCommittee("NA");
        SerializeUtility.saveObject(this, getFileName());
    }

    public List<Post> getEnquiries() {
        List<Post> myEnquiries = new ArrayList<>();
        CampRepository repo = UnifiedCampRepository.getInstance();
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

    public boolean removeCamp(String campName){
        //Prevent user from withdrawing if they are in the Committee
        if(this.myCommittee.equalsIgnoreCase(campName))
            return false;
        super.removeCamp(campName);
        SerializeUtility.saveObject(this, getFileName());
        return true;
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
    public int viewAllCamps() {
        CampRepository repo = UnifiedCampRepository.getInstance();

        // Get camps from both categories in a hashset of unique values to avoid duplicates
        Set<Camp> allCamps = new HashSet<>();
        allCamps.addAll(repo.filterCampByFaculty(Faculty.ALL));
        allCamps.addAll(repo.filterCampByFaculty(this.getFaculty()));

        if (allCamps.isEmpty()) {
            System.out.println("No camps available.");
            return 0;
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
        return allCamps.size();
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
}
