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
    public int displayMyCamps(){
        int index = 0;
        try{
            index = -1;
            UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
            System.out.println("My Created Camps: ");
            for(String c: this.getMyCamps()){
                System.out.println("_________________________________");
                System.out.println("Index: " + index++);
                Camp camp = repo.retrieveCamp(c);
                camp.display();
            }
        } catch (NullPointerException e){
            System.out.println("No camps created.");
        }
        return index;
    }

    public void deleteCamp(Camp camp){
        OrganiserActions action = new CampDeleter();
        action.manageCamp();
    }
    public void editCamp(Camp camp){
        OrganiserActions action = new CampEditor();
        action.manageCamp();
    }
    public void createCamp(Camp camp){
        OrganiserActions action = new CampCreator();
        action.manageCamp();
    }

    public void assignCamp(String UserID, Camp camp){
        OrganiserActions action = new Assigner();
        action.manageCamp();
    }
}
