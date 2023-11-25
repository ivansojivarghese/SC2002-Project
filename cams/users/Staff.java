package cams.users;

import cams.Camp;
import cams.dashboards.DashboardState;
import cams.dashboards.StaffMenuState;
import cams.database.CampRepository;
import cams.post_types.Post;
import cams.util.Faculty;
import cams.database.UnifiedCampRepository;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Staff extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 565197102100995754L; //crc32b Hash of "Staff" converted to ASCII
    private static final String folderName = "users";
    public Staff(String name, String userID, Faculty faculty) {
        super(name, userID, faculty);
        savable.saveObject(this, folderName, this.getFileName());
    }

    public DashboardState getMenuState() {
        return new StaffMenuState();
    }

    public String getFileName() {
        return "Staff_" + this.getUserID().replaceAll("\\s+", "_") + ".ser";
        //TODO error?
    }

    public List<Post> getEnquiries() { //COLLECTS all enquiries in Camps created by Staff
        List<Post> myEnquiries = new ArrayList<>();
        try{
            UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
            for (String campName : this.getMyCamps()) {
                Camp camp = repo.retrieveCamp(campName);
                if(camp == null)
                    continue;
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
    public int viewAllCamps() {
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

    @Override
    public List<Post> getSuggestions() {
        List<Post> mySuggestions = new ArrayList<>();
        try{
            UnifiedCampRepository repo = UnifiedCampRepository.getInstance();
            for (String campName : this.getMyCamps()) {
                Camp camp = repo.retrieveCamp(campName);
                if(camp == null)
                    continue;
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
        int index = 0;
        System.out.println("My Created Camps: ");
        try {
            index = super.displayMyCamps();
        } catch (NullPointerException e){
            System.out.println("No camps created.");
        }
        if(index == 0)
            System.out.println("No camps created.");
        return index;
    }
}
