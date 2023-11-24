package cams.dashboards;

import cams.dashboards.enquiry_menus.Enquirer;
import cams.users.Participant;
import cams.users.ParticipantAction;
import cams.users.Student;
import cams.users.User;

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

        // Display options of the main menu
        this.mainMenu();

        //GET user choice
        while(true) {
            try {
                System.out.print("SELECT AN ACTION: ");
                input = sc.nextLine().strip();
                option = Integer.parseInt(input);
                if(option >= 1 && option <= 6)
                    break;
                System.out.println("Invalid input, please try again.");
            }
            catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println();

        menuLogic(option);
    }
    protected void mainMenu(){
        // Code to display options
        System.out.println("                          DASHBOARD                           ");
        System.out.println("______________________________________________________________");
        Student student = (Student) user;
        System.out.println("STUDENT Name: " + user.getName());
        System.out.println("Username: " + user.getUserID());
        System.out.println("Faculty: " + user.getFaculty());
        System.out.println("Committee Member: " + student.getCommittee());
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
