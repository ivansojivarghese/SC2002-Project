package cams.dashboards.suggestion_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.MenuAction;
import cams.dashboards.post_menus.PostApproverUI;
import cams.users.User;
import cams.util.InputScanner;
import cams.util.UserInput;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class ApproverUI extends SuggestionViewerUI implements PostApproverUI {
    /**
     * Displays the enquiry menu to the user and handles user input for various enquiry operations.
     * @param dashboard The dashboard through which the user interacts.
     */
    @Override
    public void display(Dashboard dashboard) {
        try{
            // Displays the suggestions of the user and gets the user's number of suggestions to approve
            int numSuggestions = view(dashboard.getAuthenticatedUser());
            System.out.println();

            if (numSuggestions < 1){
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
        }
    //At the end of the display method, the main APP will redisplay the set menu
    }

    /**
     * Initialises a hashmap of methods corresponding to each choice in the user menu
     * @param dashboard The dashboard object
     * @param numSuggestions The number of enquiries that the user has submitted
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

    protected void approveSuggestion(User user, int numSuggestions) {
        Scanner sc = InputScanner.getInstance();
        int postIndex = UserInput.getIntegerInput(0, numSuggestions-1, "Enter index of suggestion: ");
        boolean isApproved = UserInput.getBoolInput("Enter 0 to reject, or 1 to approve: ");

        try {
            if (approve(user, postIndex, isApproved))
                System.out.println("Success!");
        } catch (Exception e) {
            System.out.println("Unsuccessful: " + e.getMessage());
        }
    }

    public abstract boolean approve(User user, int postIndex, boolean isApproved);
}
