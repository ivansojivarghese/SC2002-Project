package cams.dashboards;

import cams.dashboards.enquiry_menus.Enquirer;
import cams.users.Participant;
import cams.users.ParticipantAction;
import cams.users.Student;
import cams.users.User;
import cams.util.UserInput;

import java.util.Scanner;

public class StudentMenuState implements DashboardState{
    private final Participant participant;
    private Dashboard dashboard;
    private User user;
    public StudentMenuState(){
        this.participant = new ParticipantAction();
    }
    @Override
    public void display(Dashboard dashboard) {
        //Initialise Dashboard variable
        this.dashboard = dashboard;
        this.user = dashboard.getAuthenticatedUser();

        //Initialise variables and scanner for user input
        int option;
        String input;
        Scanner sc = new Scanner(System.in);

        //Display User information
        this.userInfo();
        // Display options of the main menu
        this.mainMenu();

        //GET user choice
        option = UserInput.getIntegerInput(1, 6, "SELECT ACTION: ");
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

    protected void menuLogic(int option){
        String userInput;
        Scanner sc = new Scanner(System.in);

        switch (option) {
            case 1 -> { //change password
                String newPassword;
                System.out.println("Enter new password (Case Sensitive):");
                newPassword = sc.nextLine();
                user.setPassword(newPassword);
                System.out.println("Password successfully changed!");
            }
            case 2 -> //logout
                    dashboard.logout();
            case 3 -> //View registered camps
                    user.displayMyCamps();
            case 4 -> { //Register
                user.viewAllCamps();
                System.out.println();
                System.out.println("Enter name of camp to register for: ");
                userInput = sc.nextLine();

                participant.register(user, userInput);
                dashboard.loggedIn(); //refresh dashboard logged in state in case user has become committee member
            }
            case 5 -> { //Withdraw
                //Only participants may withdraw from camps
                System.out.println("Enter name of camp to withdraw from: ");
                userInput = sc.nextLine();
                participant.deregister(user, userInput);
            }
            case 6 -> dashboard.setState(new Enquirer());
        }
    }
}
