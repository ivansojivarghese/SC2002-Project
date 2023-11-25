package cams.dashboards.enquiry_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.MenuAction;
import cams.dashboards.post_menus.PosterUI;
import cams.users.Committable;
import cams.users.User;
import cams.util.InputScanner;
import cams.util.UserInput;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Abstract class representing the user interface for enquiry operations.
 * This class provides the template for enquiry related actions such as submitting,
 * editing, and deleting enquiries.
 */
public abstract class EnquirerUI extends EnquiryViewerUI implements PosterUI {

    public EnquirerUI() {}

    /**
     * Displays the enquiry menu to the user and handles user input for various enquiry operations.
     * @param dashboard The dashboard through which the user interacts.
     */
    @Override
    public void display(Dashboard dashboard) {
        try {
            // Displays the enquiries of the user and gets the user's number of enquiries
            int numEnquiries = view(dashboard.getAuthenticatedUser());
            System.out.println();

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

        actions.put(1, () -> submitEnquiry(user));
        if (numEnquiries > 0) {
            actions.put(2, () -> editEnquiry(user, numEnquiries));
            actions.put(3, () -> deleteEnquiry(user, numEnquiries));
        }
        actions.put(0, dashboard::loggedIn);

        return actions;
    }

    /**
     * Displays the corresponding description of each menu choice
     * @param actions Hash map of methods keyed to menu choices
     */
    protected void describeOptions(Map<Integer, MenuAction> actions) {
        for (Integer key : actions.keySet()) {
            switch (key) {
                case 1 -> System.out.println("(1) Submit a new enquiry");
                case 2 -> System.out.println("(2) Edit an enquiry");
                case 3 -> System.out.println("(3) Delete an enquiry");
                case 0 -> System.out.println("(0) Back to user dashboard");
            }
        }
    }

    protected void submitEnquiry(User user) {
        Scanner sc = InputScanner.getInstance();
        System.out.println("Name of camp you are enquiring about: ");
        String campName = sc.nextLine();
        // Prevent committee members from submitting enquiries to their camp
        if(user instanceof Committable){
            if(campName.equalsIgnoreCase(((Committable) user).getCommittee()))
            {
                System.out.println("Committee members cannot enquire about their own camp.");
                return;
            }
        }
        System.out.println("Input query: ");
        String content = sc.nextLine();
        try {
            if (submit(campName, user.getUserID(), content))
                System.out.println("Enquiry successfully submitted!");
        } catch (NullPointerException e) {
            System.out.println("Submission unsuccessful: " + e.getMessage());
        }
    }

    protected void deleteEnquiry(User user, int numEnquiries){
        int option = UserInput.getIntegerInput(0, numEnquiries-1, "Enter index of enquiry to delete: ");
        try {
            if (delete(user, option))
                System.out.println("Enquiry successfully deleted!");
        } catch (NullPointerException e) {
            System.out.println("Deletion unsuccessful: " + e.getMessage());
        }
    }
    protected void editEnquiry(User user, int numEnquiries){
        Scanner sc = InputScanner.getInstance();
        int option = UserInput.getIntegerInput(0, numEnquiries-1, "Enter index of enquiry to edit: ");
        System.out.println("Input modified query: ");
        String content = sc.nextLine();
        try {
            if (edit(user, option, content))
                System.out.println("Enquiry successfully edited!");
        } catch (NullPointerException e) {
            System.out.println("Editing unsuccessful: " + e.getMessage());
        }
    }

    public abstract boolean submit(String campName, String userID, String text);
    public abstract boolean edit(User user, int postIndex, String content);
    public abstract boolean delete(User user, int postIndex);
}
