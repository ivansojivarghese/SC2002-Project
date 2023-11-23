package cams.dashboards;

import cams.dashboards.enquiry_menus.Enquirer;
import cams.dashboards.enquiry_menus.Replier;
import cams.dashboards.suggestion_menus.Suggester;
import cams.users.Participant;
import cams.users.Student;
import cams.users.User;

import java.util.Scanner;

public class StudentMenuState implements DashboardState{
    @Override
    public void display(Dashboard dashboard) {
        int option;
        String userInput;

        Scanner sc = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();

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

        if(((Student) user).isCommittee())
        {
            System.out.println("(7) View suggestions menu");
            System.out.println("(8) Reply to enquiries for my camp");
        }
        System.out.print("SELECT AN ACTION: ");
        option = sc.nextInt();
        sc.nextLine(); //consume new line
        System.out.println();

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
                //TODO fix student view all camps; doesn't show
                user.viewAllCamps();
                //TODO move this to a "Camp Registration Menu"
                //TODO prevent users from withdrawing when they don't have any camps
                //TODO prevent users from registering when no camps exist
                System.out.println();
                System.out.println("Enter name of camp to register for: ");
                userInput = sc.nextLine();
                ((Participant) user).register(userInput);
            }
            case 5 -> { //Withdraw
                //Only participants may withdraw from camps
                System.out.println("Enter name of camp to withdraw from: ");
                userInput = sc.nextLine();
                ((Participant) user).deregister(userInput);
            }
            case 6 -> dashboard.setState(new Enquirer());
            case 7 -> dashboard.setState(new Suggester());
            case 8 -> dashboard.setState(new Replier());
        }
    }
}
