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

/**
 * Represents a staff member specialisation of {@link User}.
 * Object is {@link Serializable}.
 * Staff members have specific functionalities related to managing camps including processing enquiries and suggestions.
 */
public class Staff extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 565197102100995754L; // CRC32b hash of "Staff" converted to ASCII
    private static final String folderName = "users";

    /**
     * Constructs a Staff member with the specified details and saves it.
     *
     * @param name     The name of the staff member.
     * @param userID   The user ID of the staff member.
     * @param faculty  The faculty to which the staff member belongs.
     */
    public Staff(String name, String userID, Faculty faculty) {
        super(name, userID, faculty);
        savable.saveObject(this, folderName, this.getFileName());
    }

    /**
     * Retrieves the appropriate menu state for a staff member.
     *
     * @return The {@link DashboardState} specific to staff members.
     */
    public DashboardState getMenuState() {
        return new StaffMenuState();
    }

    /**
     * Generates the file name for saving staff member data.
     *
     * @return A {@link String} representing the file name.
     */
    public String getFileName() {
        return "Staff_" + this.getUserID().replaceAll("\\s+", "_") + ".ser";
        //TODO error?
    }

    /**
     * Collects all enquiries related to the camps created by the staff member.
     *
     * @return A list of {@link Post} objects representing enquiries.
     */
    @Override
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

    /**
     * This method is not implemented.
     */
    @Override
    public void addEnquiry(Post post) {
        throw new UnsupportedOperationException("This method is not implemented. Staffs do not post enquiries.");
    }

    /**
     * Displays all available camps.
     *
     * @return The number of camps displayed.
     */
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

    /**
     * Collects all suggestions related to the camps created by the staff member.
     *
     * @return A list of {@link Post} objects representing suggestions.
     */
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
    /**
     * Displays the camps created by the staff member
     * using a refinement of the same method in User superclass
     * @return The number of camps created by the staff member.
     */
    @Override
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
