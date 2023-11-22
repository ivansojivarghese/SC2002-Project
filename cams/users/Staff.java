package cams.users;

import cams.Camp;
import cams.post_types.Post;
import cams.util.Faculty;
import cams.database.UnifiedCampRepository;

import java.util.ArrayList;
import java.util.List;

public class Staff extends User implements Organiser{

    public Staff() {}

    public Staff(String name, String userID, Faculty faculty) {
        super(name, userID, faculty);
        this.setPassword("password");
    }

    public List<Post> getEnquiries() { //COLLECTS all enquiries in Camps created by Staff
        List<Post> myEnquiries = new ArrayList<>();
        try{
            UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
            for (String campName : this.getMyCamps()) {
                Camp camp = repo.retrieveCamp(campName);
                myEnquiries.addAll(camp.getEnquiries());
            }
        }
        catch (NullPointerException e){
                System.out.println("No camp to retrieve suggestions from.");
        }
        return myEnquiries;
    }

    //TODO implement this method to return all camps
    @Override
    public void viewAllCamps() {
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for(Camp c : repo.allCamps()){
            System.out.println("_________________________________");
            c.display();
        }
    }

    @Override
    public List<Post> getSuggestions() {
        List<Post> mySuggestions = new ArrayList<>();
        try{
            UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
            for (String campName : this.getMyCamps()) {
                Camp camp = repo.retrieveCamp(campName);
                mySuggestions.addAll(camp.getSuggestions());
            }
        }
        catch (NullPointerException e){
            System.out.println("No camp to retrieve suggestions from.");
        }
        return mySuggestions;
    }

    //Refinement of displayMyCamps() method in User superclass
    public int displayMyCamps(){
        System.out.println("My Created Camps: ");
        int index = super.displayMyCamps();
        if(index == 0)
            System.out.println("No camps created.");
        return index;
    }

    public void deleteCamp(String campName){
        OrganiserActions action = new CampDeleter();
        action.manageCamp(this, campName);
    }
    public void editCamp(String campName){
        OrganiserActions action = new CampEditor();
        action.manageCamp(this, campName);
    }
    public void createCamp(String campName){
        OrganiserActions action = new CampCreator();
        action.manageCamp(this, campName);
    }

    public void assignCamp(String UserID, String campName){
        OrganiserActions action = new Assigner();
        action.manageCamp(this, campName);
    }
}
