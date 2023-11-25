package cams.dashboards;

import cams.dashboards.enquiry_menus.Enquirer;
import cams.users.Participant;
import cams.users.ParticipantAction;
import cams.users.User;
import cams.util.UserInput;

import java.util.Scanner;

/**
 Represents the state of the dashboard when a student is logged in.
 **/
public class StudentMenuState implements DashboardState{
    protected final Participant participant;
    protected Dashboard dashboard;
    protected User user;

    public StudentMenuState(){
        this.participant = new ParticipantAction();
    }
    @Override
    public void display(Dashboard dashboard) {
        //Initialise Dashboard and User variables
        this.dashboard = dashboard;
        this.user = dashboard.getAuthenticatedUser();

        // Null check for dashboard
        if (dashboard == null) {
            throw new IllegalArgumentException("Dashboard cannot be null");
        }
        // Null check for user
        if (user == null) {
            throw new IllegalStateException("User cannot be null in dashboard");
        }

        // Display User information and the main menu
        this.userInfo();
        this.mainMenu();

        // Get user choice and process it
        int option = UserInput.getIntegerInput(1, 6, "SELECT ACTION: ");
        System.out.println();

        menuLogic(option);
    }

    protected void userInfo(){
        // Code to display options
        System.out.println("                          DASHBOARD                           ");
        System.out.println("______________________________________________________________");

        System.out.println("STUDENT Name: " + user.getName());
        System.out.println("Username: " + user.getUserID());
        System.out.println("Faculty: " + user.getFaculty());
    }

    /**
     * Displays the main menu options for the student.
     **/
    protected void mainMenu(){
        System.out.println("______________________________________________________________");
        System.out.println("                             MENU                              ");
        System.out.println("(1) Change your password");
        System.out.println("(2) Logout");
        System.out.println("(3) View my Camps");

        System.out.println("(4) Register for a Camp");
        System.out.println("(5) Withdraw from a Camp");
        System.out.println("(6) View enquiries menu");
    }

    /**
     * Processes the selected menu option.
     * @param option The user-selected menu option.
     **/
    protected void menuLogic(int option){
        Scanner sc = new Scanner(System.in);

        switch (option) {
            case 1 -> { // Change password
                System.out.println("Enter new password (Case Sensitive):");
                String newPassword = sc.nextLine();
                user.setPassword(newPassword);
                System.out.println("Password successfully changed!");
            }
            case 2 -> // Logout
                    dashboard.logout();
            case 3 -> // View registered camps
                    user.displayMyCamps();
            case 4 -> { // Register for a camp
                user.viewAllCamps();
                System.out.println();
                System.out.println("\nEnter name of camp to register for: ");
                String registerInput = sc.nextLine();

                participant.register(user, registerInput);
                dashboard.loggedIn(); // Refresh dashboard state
            }
            case 5 -> { // Withdraw from a camp
                System.out.println("Enter name of camp to withdraw from: ");
                String deregisterInput = sc.nextLine();
                participant.deregister(user, deregisterInput);
            }
            case 6 -> { // View enquiries menu
                dashboard.setState(new Enquirer());
            }
        }
    }
}
