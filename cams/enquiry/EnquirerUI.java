package cams.enquiry;

import cams.enquiry.enquirer_controller.EnquirerController;
import cams.enquiry.enquirer_controller.StudentEnquirerController;
import cams.dashboards.Dashboard;
import cams.dashboards.MenuAction;
import cams.post_menus.PosterUI;
import cams.post_types.Post;
import cams.users.Committable;
import cams.users.User;
import cams.util.InputScanner;
import cams.util.UserInput;

import java.util.*;

/**
 * Class representing the user interface for enquiry operations.
 * This class provides the template for enquiry related actions such as submitting,
 * editing, and deleting enquiries.
 * It extends {@link EnquirerUI} and provides the implementation for the reply method in {@link PosterUI}.
 */
public class EnquirerUI implements PosterUI {
    //Instantiate dependency
    private final EnquirerController enquirerController = new StudentEnquirerController();
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

            // If attempt to view enquiries returns -1 meaning no camps available
            if(numEnquiries == -1) {
                System.out.println("No camps available to enquire about.");
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
    /**
     * Handles the submission of a new enquiry from a user.
     * <p>
     * This method prompts the user for the name of the camp they are enquiring about and the content of their enquiry.
     * It also checks if the user is a committee member trying to enquire about their own camp, which is not allowed.
     * @param user The user submitting the enquiry.
     */
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

        // Attempt to submit the enquiry
        try {
            if (submit(campName, user, content))
                System.out.println("Enquiry successfully submitted!");
        } catch (NullPointerException e) {
            System.out.println("Submission unsuccessful: " + e.getMessage());
        }
    }

    /**
     * Handles the deletion of an existing enquiry.
     * <p>
     * The user is prompted to select the index of the enquiry they wish to delete.
     * @param user         The user who owns the enquiry.
     * @param numEnquiries The total number of enquiries the user has.
     */
    protected void deleteEnquiry(User user, int numEnquiries){
        int option = UserInput.getIntegerInput(0, numEnquiries-1, "Enter index of enquiry to delete: ");
        try {
            if (delete(user, option))
                System.out.println("Enquiry successfully deleted!");
        } catch (NullPointerException e) {
            System.out.println("Deletion unsuccessful: " + e.getMessage());
        }
    }

    /**
     * Handles editing an existing enquiry.
     * <p>
     * The user is prompted to select the index of the enquiry they wish to edit and provide the new content.
     * @param user         The user who owns the enquiry.
     * @param numEnquiries The total number of enquiries the user has.
     */
    protected void editEnquiry(User user, int numEnquiries){
        Scanner sc = InputScanner.getInstance();
        int option = UserInput.getIntegerInput(0, numEnquiries-1, "Enter index of enquiry to edit: ");
        System.out.println("Input modified query: ");
        String content = sc.nextLine();

        // Attempt to edit the enquiry
        try {
            if (edit(user, option, content))
                System.out.println("Enquiry successfully edited!");
        } catch (NullPointerException e) {
            System.out.println("Editing unsuccessful: " + e.getMessage());
        }
    }

    /**
     * Submits a new post to a specified camp.
     *
     * @param campName The name of the camp to which the post is being submitted.
     * @param user     The user submitting the post.
     * @param text     The content of the post.
     * @return true if the submission is successful, false otherwise.
     */
    @Override
    public boolean submit(String campName, User user, String text) {
        return enquirerController.submit(campName, user, text);
    }

    /**
     * Edits an existing {@link Post post}.
     *
     * @param user      The user who owns the post.
     * @param postIndex The index of the post in the user's list of posts.
     * @param content   The new content for the post.
     * @return true if the edit is successful, false otherwise.
     */
    @Override
    public boolean edit(User user, int postIndex, String content) {
        return enquirerController.edit(user, postIndex, content);
    }

    /**
     * Deletes a post.
     *
     * @param user      The user who owns the post.
     * @param postIndex The index of the post in the user's list of posts.
     * @return true if the deletion is successful, false otherwise.
     */
    @Override
    public boolean delete(User user, int postIndex) {
        return enquirerController.delete(user, postIndex);
    }

    /**
     * Displays the enquiries of the user and returns the count of those enquiries.
     * Each enquiry is displayed with its index and content.
     *
     * @param user The user whose enquiries are to be displayed.
     * @return The number of enquiries displayed.
     */
    @Override
    public int view(User user){
        return enquirerController.view(user);
    }
}
