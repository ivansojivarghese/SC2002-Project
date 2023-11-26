package cams.users;

import cams.approver.approver_controller.ApproverController;
import cams.dashboards.DashboardState;
import cams.dashboards.menu.StaffUI;
import cams.replier.replier_controller.ReplierController;
import cams.replier.replier_controller.StaffReplierController;
import cams.approver.approver_controller.StaffApproverController;
import cams.post_types.Post;
import cams.util.Faculty;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a staff member specialisation of {@link User}.
 * Object is {@link Serializable}.
 * Staff members have specific functionalities related to managing camps including processing enquiries and suggestions.
 */
public class Staff extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 565197102100995754L; // CRC32b hash of "Staff" converted to ASCII
    private static final String folderName = "users";
    private static final ReplierController replier = new StaffReplierController();;
    private static final ApproverController approver = new StaffApproverController();;

    /**
     * Empty constructor for staff
     */
    public Staff(){}
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
        return new StaffUI();
    }

    /**
     * Generates the file name for saving staff member data.
     *
     * @return A {@link String} representing the file name.
     */
    public String getFileName() {
        return "Staff_" + this.getUserID().replaceAll("\\s+", "_") + ".ser";
    }

    /**
     * Returns the replier controller dependency used by Staff
     * @return
     */
    public ReplierController getReplierController(){
        return replier;
    }

    /**
     * Collects all enquiries related to the camps created by the staff member.
     *
     * @return A list of {@link Post} objects representing enquiries.
     */
    @Override
    public List<Post> getEnquiries() { //COLLECTS all enquiries in Camps created by Staff
        return replier.getEnquiries(this);
    }

    /**
     * Collects all suggestions related to the camps created by the staff member.
     *
     * @return A list of {@link Post} objects representing suggestions.
     */
    @Override
    public List<Post> getSuggestions() {
        return new ArrayList<>(approver.getSuggestions(this));
    }
    /**
     * Displays the camps created by the staff member
     * using a refinement of the same method in User superclass
     * @return The number of camps created by the staff member.
     */
}
