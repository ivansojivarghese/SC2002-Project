package cams.users;

import cams.Camp;
import cams.post_types.Post;
import cams.util.Faculty;
import cams.database.UnifiedCampRepository;

import java.util.List;

public class Staff extends User {

    public Staff() {}

    public Staff(String name, String userID, Faculty faculty) {
        super(name, userID, faculty);
        this.setPassword("password");
    }

    public List<Post> getEnquiries() { //COLLECTS all enquiries in Camps created by Staff
        List<Post> myEnquiries = null;
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for (String campName : this.getMyCamps()) {
            Camp camp = repo.retrieveCamp(campName);
            myEnquiries.addAll(camp.getEnquiries());
        }
        return myEnquiries;
    }

    //TODO implement this method to return all camps
    @Override
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
            mySuggestions.addAll(camp.getSuggestions());
        }
        return mySuggestions;
    }
    public void displayMyCamps(){
        UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
        for(String c: this.getMyCamps()){
            Camp camp = repo.retrieveCamp(c);
            camp.display();
            System.out.println("______________");
        }
    }
    

}
