package cams.dashboards.enquiry_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.MenuAction;
import cams.dashboards.post_menus.PostReplierUI;
import cams.post_types.Post;
import cams.users.User;
import cams.util.InputScanner;
import cams.util.UserInput;

import java.util.*;

/**
 * Abstract class representing the user interface for replying to {@link cams.post_types.Enquiry enquiries}, a specific type of {@link cams.post_types.Post post}.
 * This class provides a UI with abstracted logic for the action of replying to enquiries,
 * including displaying a menu to choose an enquiry to reply to, and handling the user interaction.
 * It extends {@link EnquiryViewerUI} and implements {@link PostReplierUI}
 */
public class ReplierUI extends EnquiryViewerUI implements PostReplierUI {
    private ReplierController replierController;
    /**
     * Constructs a ReplierUI instance.
     */
    public ReplierUI() {
    }
    /**
     * Displays the replierController menu to the user and allows the user to select an enquiry to reply to.
     * Subsequently, the method facilitates the reply process.
     * If there are no enquiries, it redirects back to the main menu of the dashboard.
     *
     * @param dashboard The dashboard through which the user interacts.
     */
    @Override
    public void display(Dashboard dashboard) {
        //Polymorphism to get the right controller based on the user type
        this.replierController = dashboard.getAuthenticatedUser().getReplierController();

        try {
            // Displays the enquiries of the user and gets the user's number of enquiries
            int numEnquiries = view(dashboard.getAuthenticatedUser());
            System.out.println();

            if(numEnquiries < 1) {
                dashboard.loggedIn();
                return;
            }

            // Initialise and display hash map of menu options storing methods
            Map<Integer, MenuAction> options = initializeActions(dashboard, numEnquiries);
            describeOptions(options);

            // Get user's choice
            int option = UserInput.getIntegerInput(0, options.size() - 1, "SELECT AN ACTION: ");

            // Executes the lambda expression associated with the user's option
            options.getOrDefault(option, () -> System.out.println("Invalid option selected")).execute();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        //At the end of the display method, the main APP will redisplay the set menu
    }

    /**
     * Initialises a hashmap of methods corresponding to each choice in the user menu
     * @param dashboard The dashboard object
     * @param numEnquiries The number of enquiries that the user has submitted
     * @return Hash map of methods keyed to menu choices
     */
    protected Map<Integer, MenuAction> initializeActions(Dashboard dashboard, int numEnquiries) {
        Map<Integer, MenuAction> actions = new HashMap<>();
        User user = dashboard.getAuthenticatedUser();

        if (numEnquiries > 0) {
            actions.put(1, () -> replyEnquiry(user, numEnquiries));
            actions.put(0, dashboard::loggedIn);
        }
        return actions;
    }

    /**
     * Displays the corresponding description of each menu choice
     * @param actions Hash map of methods keyed to menu choices
     */
    protected void describeOptions(Map<Integer, MenuAction> actions) {
        for (Integer key : actions.keySet()) {
            switch (key) {
                case 1 -> System.out.println("(1) Reply to an enquiry");
                case 0 -> System.out.println("(0) Back to user dashboard");
            }
        }
    }

    /**
     * Method that gets user input for replying an enquiry
     * @param user The current authenticated user replying
     * @param numEnquiries The user's total number of enquiries to reply
     */
    protected void replyEnquiry(User user, int numEnquiries) {
        Scanner sc = InputScanner.getInstance();
        String content;
        int option = UserInput.getIntegerInput(0, numEnquiries-1, "Enter index of enquiry to reply: ");
        do {
            System.out.println("Input reply: ");
            content = sc.nextLine();
        }
        while (Objects.equals(content.strip(), ""));
        try {
            if (reply(user, option, content))
                System.out.println("Success!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Replies to a specific enquiry.
     *
     * @param user      The user performing the reply.
     * @param postIndex The index of the post to reply to.
     * @param reply     The reply content.
     * @return true if the reply was successful, false otherwise.
     */
    @Override
    public boolean reply(User user, int postIndex, String reply){
        return replierController.reply(user, postIndex, reply);
    }
    /**
     * Displays the enquiries of the user to reply to and returns the count of those enquiries.
     * Each enquiry is displayed with its index and content.
     *
     * @param user The user whose enquiries to reply to are to be displayed.
     * @return The number of enquiries displayed.
     */
    public int view(User user){
        Post currentPost;
        System.out.println("My Camp's Enquiries: ");
        List<Post> myEnquiries = new ArrayList<>(replierController.getEnquiries(user));

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
