package cams.dashboards.enquiry_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.MenuAction;
import cams.dashboards.post_menus.PostReplierUI;
import cams.users.User;
import cams.util.InputScanner;
import cams.util.UserInput;

import java.util.*;

/**
 * Abstract class representing the user interface for replying to enquiries
 * This class provides the template for the action of replying enquiries
 */
public abstract class ReplierUI extends EnquiryViewerUI implements PostReplierUI {
    public ReplierUI() {
    }
    /**
     * Displays the replier menu to the user and handles user input for reply operation.
     * @param dashboard The dashboard through which the user interacts.
     */
    public void display(Dashboard dashboard) {
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

    @Override
    public abstract boolean reply(User user, int postIndex, String reply);
}
