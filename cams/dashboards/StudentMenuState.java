package cams.dashboards;

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
        System.out.println("______________________________________________________________");
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
        System.out.printf("SELECT AN ACTION: ");
        option = sc.nextInt();
        System.out.println(); //consume new line

        switch (option){
            case 1: //change password
                String newPassword;

                System.out.println("Enter new password:");
                newPassword = sc.next();
                user.setPassword(newPassword);
                System.out.println("Password successfully changed!");
                break;

            case 2: //logout
                dashboard.logout();
                break;
            case 3: //View registered camps
                user.displayMyCamps();
                break;
            case 4: //Register
                user.viewAllCamps(); //maybe put this method for viewing available camps in camp repo instead
                if(user instanceof Participant){
                    System.out.println("Enter name of camp to register for: ");
                    userInput = sc.next();
                    ((Participant) user).Register(userInput);
                }
                break;

            case 5: //Withdraw
                if(user instanceof Participant) {
                    System.out.println("Enter name of camp to withdraw from: ");
                    userInput = sc.next();
                    ((Participant) user).Deregister(userInput);
                }
                break;
            case 6:
                dashboard.enquirerMenu();
                break;
            case 7:
                dashboard.suggesterMenu();
                break;
            case 8:
                dashboard.replierMenu();
                break;
        }
    }
}
