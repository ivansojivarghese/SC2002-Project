package cams.enquiry_menus;

import cams.dashboards.Dashboard;
import cams.post_menus.PostViewerUI;
import cams.post_types.Post;
import cams.users.User;

import java.util.ArrayList;
import java.util.List;
/**
 * Abstract class functioning as a UI component for viewing enquiries.
 * This class provides the basic functionalities to display enquiries for a user.
 * It implements {@link PostViewerUI} to comply with the general structure of post viewing in the system.
 */
public abstract class EnquiryViewerUI implements PostViewerUI {
    /**
     * Constructs an EnquiryViewerUI instance.
     */
    public EnquiryViewerUI(){}
    /**
     * Displays the enquiries associated with the current user.
     * This method fetches the user from the given dashboard and displays their enquiries.
     * If no enquiries are present, it notifies the user accordingly.
     *
     * @param dashboard The dashboard context from which the user and enquiries are fetched.
     */
    public void display(Dashboard dashboard){
        User user = dashboard.getAuthenticatedUser();

        if (view(user) == 0)
            System.out.println("No enquiries to display");

        // Returns the dashboard to its main user menu
        dashboard.loggedIn();
    }
    /**
     * Displays the enquiries of the user and returns the count of those enquiries.
     * Each enquiry is displayed with its index and content.
     *
     * @param user The user whose enquiries are to be displayed.
     * @return The number of enquiries displayed.
     */
    public int view(User user){
        Post currentPost;
        System.out.println("My Camp Enquiries: ");
        List<Post> myEnquiries = new ArrayList<>(user.getEnquiries());

        // Check if the user has no enquiries
        if(myEnquiries.isEmpty()){
            System.out.println("No enquiries to display.");
            return 0;
        }

        // Display each enquiry with its index and content
        for (int i = 0; i < myEnquiries.size(); i++) {
            currentPost = myEnquiries.get(i);
            System.out.println("Index " + i + ": ");
            currentPost.displayContent();
            System.out.println("__________________________");
        }
        return myEnquiries.size();
    }
}
