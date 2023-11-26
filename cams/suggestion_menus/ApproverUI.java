package cams.suggestion_menus;

import cams.controllers.approver.ApproverController;
import cams.controllers.approver.StaffApproverController;
import cams.dashboards.Dashboard;
import cams.dashboards.MenuAction;
import cams.post_menus.PostApproverUI;
import cams.users.User;
import cams.util.UserInput;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class providing the user interface for the approval process of posts.
 * Implements {@link PostApproverUI} to handle the display and interaction logic for post approvals.
 */
public class ApproverUI extends SuggestionViewerUI implements PostApproverUI {
    private final ApproverController approverController = new StaffApproverController();
    /**
     * Displays the approval menu to the user and handles user input for various approval operations.
     * <p>
     * This method displays the list of suggestions to the user and allows them to select a suggestion
     * to approve or reject.
     *
     * @param dashboard The dashboard through which the user interacts.
     */
    @Override
    public void display(Dashboard dashboard) {
        try{
            // Displays the suggestions of the user and gets the user's number of suggestions to approve
            int numSuggestions = view(dashboard.getAuthenticatedUser());
            System.out.println();

            if (numSuggestions < 1){
                System.out.println("Returning to dashboard...");
                dashboard.loggedIn();
                return;
            }

            // Initialise and display hash map of menu options storing methods
            Map<Integer, MenuAction> options = initializeActions(dashboard, numSuggestions);
            describeOptions(options);

            // Get user's choice
            int option = UserInput.getIntegerInput(0, options.size() - 1, "SELECT AN ACTION: ");

            // Executes the lambda expression associated with the user's option
            options.getOrDefault(option, () -> System.out.println("Invalid option selected")).execute();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            dashboard.loggedIn();
        }
    //At the end of the display method, the main APP will redisplay the set menu
    }

    /**
     * Initialises a hashmap of methods corresponding to each choice in the user menu
     * @param dashboard The dashboard object
     * @param numSuggestions The total number of suggestions that the user has in their camps
     * @return Hash map of methods keyed to menu choices
     */
    protected Map<Integer, MenuAction> initializeActions(Dashboard dashboard, int numSuggestions) {
        Map<Integer, MenuAction> actions = new HashMap<>();
        User user = dashboard.getAuthenticatedUser();

        actions.put(1, () -> approveSuggestion(user, numSuggestions));
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
                case 1 -> System.out.println("(1) Approve/Reject a suggestion");
                case 0 -> System.out.println("(0) Back to user dashboard");
            }
        }
    }

    /**
     * UI process for getting user to approve or reject o a post
     * Specific logic is implemented by {@link #approve(User, int, boolean)}
     * @param user The user approving the suggestion
     * @param numSuggestions The total number of suggestions that the user has in their camps
     */
    protected void approveSuggestion(User user, int numSuggestions) {
        int postIndex = UserInput.getIntegerInput(0, numSuggestions-1, "Enter index of suggestion: ");
        boolean isApproved = UserInput.getBoolInput("Enter 0 to reject, or 1 to approve: ");

        try {
            if (approve(user, postIndex, isApproved))
                System.out.println("Success!");
        } catch (Exception e) {
            System.out.println("Unsuccessful: " + e.getMessage());
        }
    }

    /**
     *
     * @param user       The user who is performing the approval action.
     * @param postIndex  The index of the post in the list of posts to be approved.
     * @param isApproved Boolean flag indicating whether the post is approved (true) or disapproved (false).
     * @return true if the post has been successfully approved or rejected, otherwise false
     */
    public boolean approve(User user, int postIndex, boolean isApproved){
        return this.approverController.approve(user, postIndex, isApproved);
    }
}
