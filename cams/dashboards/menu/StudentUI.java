package cams.dashboards.menu;

import cams.dashboards.camp_display_controllers.CampDisplayService;
import cams.dashboards.camp_display_controllers.CampService;
import cams.dashboards.Dashboard;
import cams.dashboards.MenuAction;
import cams.dashboards.participant_controllers.ParticipationController;
import cams.dashboards.participant_controllers.StudentParticipationController;
import cams.enquiry.EnquirerUI;
import cams.users.User;
import cams.util.InputScanner;
import cams.util.UserInput;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 Represents the state of the dashboard when a student is logged in.
 **/
public class StudentUI extends UserUI{
    protected final ParticipationController participationController;

    public StudentUI(){
        this.participationController = new StudentParticipationController();
    }
    /**
     * Displays the student menu to the user and handles user input for various operations.
     * @param dashboard The dashboard through which the user interacts.
     */
    @Override
    public void display(Dashboard dashboard) {
        //Initialise Dashboard and User variables
        User user = dashboard.getAuthenticatedUser();

        // Display User information and the main menu
        this.userInfo(user);
        // Initialise and display hash map of menu options storing methods
        Map<Integer, MenuAction> options = initializeActions(dashboard);
        System.out.println("______________________________________________________________");
        System.out.println("                             MENU                              ");
        describeOptions(options);

        // Get user's choice
        int option = UserInput.getIntegerInput(0, options.size() - 1, "SELECT AN ACTION: ");

        // Executes the lambda expression associated with the user's option
        options.getOrDefault(option, () -> System.out.println("Invalid option selected")).execute();
    }
    /**
     * Initialises a hashmap of methods corresponding to each choice in the user menu
     * @param dashboard The dashboard object
     * @return Hash map of methods keyed to menu choices
     */
    protected Map<Integer, MenuAction> initializeActions(Dashboard dashboard) {
        Map<Integer, MenuAction> actions = new HashMap<>();
        User user = dashboard.getAuthenticatedUser();

        actions.put(1, () -> changePassword(user, dashboard));
        actions.put(0, dashboard::logout);
        actions.put(2, () -> displayMyCamps(user));
        actions.put(3, () -> registerCamp(dashboard));
        actions.put(4, () -> deregisterCamp(dashboard));
        actions.put(5, () -> goToEnquiries(dashboard));

        return actions;
    }

    /**
     * Displays the corresponding description of each menu choice
     * @param actions Hash map of methods keyed to menu choices
     */
    protected void describeOptions(Map<Integer, MenuAction> actions) {
        for (Integer key : actions.keySet()) {
            switch (key) {
                case 0 -> System.out.println("(0) Logout");
                case 1 -> System.out.println("(1) Change your password");
                case 2 -> System.out.println("(2) View my Camps");
                case 3 -> System.out.println("(3) Register for a Camp");
                case 4 -> System.out.println("(4) Withdraw from a Camp");
                case 5 -> System.out.println("(5) View enquiries menu");
            }
        }
    }

    /**
     * Displays information about the user on the dashboard.
     *
     * @param user The user whose information is to be displayed.
     */
    protected void userInfo(User user){
        // Code to display options
        System.out.println("                          DASHBOARD                           ");
        System.out.println("______________________________________________________________");

        System.out.println("STUDENT Name: " + user.getName());
        System.out.println("Username: " + user.getUserID());
        System.out.println("Faculty: " + user.getFaculty());
    }

    /**
     * Displays the camps registered by the student using the CampDisplayService
     *
     * @param user User whose camps are to be displayed
     * @return The number of camps joined by the student
     */
    protected int displayMyCamps(User user) {
        System.out.println("My registered camps: ");
        int index = 0;
        CampService campDisplay = new CampDisplayService();
        try {
            index = campDisplay.displayMyCamps(user);
        }
        catch (NullPointerException e){
            System.out.println("No camps registered.");
        }
        if(index == 0)
            System.out.println("No camps registered.");
        return campDisplay.displayMyCamps(user);
    }

    /**
     * Handles user interaction to register a user for a selected camp.
     * <p>
     * Prompts the user to choose a camp to register for and then proceeds with the registration.
     *
     * @param dashboard The dashboard context.
     */
    protected void registerCamp(Dashboard dashboard){
        User user = dashboard.getAuthenticatedUser();
        Scanner sc = InputScanner.getInstance();
        if(participationController.viewAllCamps(user) < 1)
            return;

        System.out.println("\nEnter name of camp to register for: ");
        String registerInput = sc.nextLine();

        participationController.register(user, registerInput);
        dashboard.loggedIn(); // Refresh dashboard state
    }

    /**
     * Handles user interaction to deregister a user from a selected camp.
     * <p>
     * Prompts the user to choose a camp to withdraw from
     * and then proceeds with the de-registration.
     *
     * @param dashboard The dashboard context.
     */
    protected void deregisterCamp(Dashboard dashboard){
        Scanner sc = InputScanner.getInstance();
        User user = dashboard.getAuthenticatedUser();
        System.out.println("\nEnter name of camp to withdraw from: ");
        String deregisterInput = sc.nextLine();
        participationController.deregister(user, deregisterInput);
    }

    /**
     * Transitions to the Enquiries state in the dashboard.
     * <p>
     * Changes the current state of the dashboard to handle enquiries.
     *
     * @param dashboard The dashboard context.
     */
    protected void goToEnquiries(Dashboard dashboard){
        dashboard.setState(new EnquirerUI());
    }
}
