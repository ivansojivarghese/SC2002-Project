package cams.dashboards.suggestion_menus;

import cams.dashboards.Dashboard;
import cams.dashboards.MenuAction;
import cams.dashboards.post_menus.PostViewerUI;
import cams.users.Committable;
import cams.users.User;
import cams.util.InputScanner;
import cams.util.UserInput;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * Abstract class providing the user interface for suggesting, editing, and deleting suggestions.
 * Implements {@link PostViewerUI} to handle the display and interaction logic for suggestion management.
 */
public class SuggesterUI extends SuggestionViewerUI implements PostViewerUI {
    private final Suggester suggester = new SuggesterService();
    /**
     * Displays the suggestion menu to the user
     * and handles user input for various suggestion operations.
     * @param dashboard The dashboard through which the user interacts.
     */
    public void display(Dashboard dashboard) {
        try {
            // Displays the enquiries of the user and gets the user's number of enquiries
            int numSuggestions = view(dashboard.getAuthenticatedUser());
            System.out.println();

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

        actions.put(1, () -> submitSuggestion(user));
        if (numSuggestions > 0) {
            actions.put(2, () -> editSuggestion(user, numSuggestions));
            actions.put(3, () -> deleteSuggestion(user, numSuggestions));
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
                case 1 -> System.out.println("(1) Submit a new suggestion");
                case 2 -> System.out.println("(2) Edit a suggestion");
                case 3 -> System.out.println("(3) Delete a suggestion");
                case 0 -> System.out.println("(0) Back to user dashboard");
            }
        }
    }

    /**
     * Handles the user interaction required for the inputs of a new suggestion.
     * Submits a new suggestion to a camp.
     * Adds one point for every suggestion added.
     * The user enters the information and the method {@link #submitSuggestion} handles the abstracted logic
     * @param user The user making the suggestion.
     */
    protected void submitSuggestion(User user){
        Scanner sc = InputScanner.getInstance();
        String campName = ((Committable) user).getCommittee();
        System.out.println("Name of camp you are suggesting to: " + campName);
        System.out.println("Input content: ");
        String content = sc.nextLine();
        try {
            if (submit(campName, user, content))
                System.out.println("Success!");
        } catch (Exception e) {
            System.out.println("Unsuccessful: " + e.getMessage());
        }
    }

    /**
     * Handles the user interaction required for editing an existing suggestion owned by the user.
     * The user selects a suggestion to edit and the method {@link #edit(User, int, String)} handles the abstracted logic
     * @param user The user editing the suggestion.
     * @param numSuggestions The total number of suggestions the user has made.
     */
    protected void editSuggestion(User user, int numSuggestions){
        Scanner sc = InputScanner.getInstance();
        int option = UserInput.getIntegerInput(0, numSuggestions-1, "Enter index of suggestion to edit: ");
        System.out.println("Input new content: ");
        String content = sc.nextLine();
        try {
            if (edit(user, option, content))
                System.out.println("Success!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Handles the user interaction required for deleting one of the user's existing suggestions.
     * The user selects a suggestion to delete and the method {@link #deleteSuggestion} handles the abstracted logic
     * @param user Current owner of the suggestion.
     * @param numSuggestions Total number of suggestions owned by the user.
     */
    protected void deleteSuggestion(User user, int numSuggestions){
        int option = UserInput.getIntegerInput(0, numSuggestions-1, "Enter index of suggestion to delete: ");
        try {
            if (delete(user, option))
                System.out.println("Success!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean submit(String camp, User user, String text){
        return suggester.submit(camp, user, text);
    }
    public boolean edit(User user, int postIndex, String content){
        return suggester.edit(user, postIndex, content);
    }
    public boolean delete(User user, int postIndex){
        return suggester.delete(user, postIndex);
    }
}
