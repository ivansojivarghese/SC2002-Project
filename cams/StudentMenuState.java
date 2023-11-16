package cams;

import cams.EnquiryHandler.Enquirer;

import java.util.Scanner;

public class StudentMenuState implements DashboardState{
    @Override
    public void display(Dashboard dashboard) {
        int option;
        String userInput;

        Scanner sc = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();

        // Code to display options
        System.out.println("-- DASHBOARD --");
            Student student = (Student) user;
            System.out.println("STUDENT Username: " + user.getUserID() + ", Faculty: " + user.getFaculty() + "Committee Member: " + student.isCommittee());

        System.out.println("-- OPTIONS --");
        System.out.println("(1) Change your password");
        System.out.println("(2) Logout");
        System.out.println("(3) View my Camps");
        System.out.println("(4) Register for a Camp");
        System.out.println("(5) Withdraw from a Camp");
        System.out.println("(6) View enquiries menu");

        option = sc.nextInt();

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

            case 3: //View registered camps
                user.displayMyCamps();

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
        }
    }
}
