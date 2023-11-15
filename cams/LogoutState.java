package cams;

import java.util.Scanner;

public class LogoutState implements DashboardState{
    @Override
    public void display(Dashboard dashboard) {
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        String loginID;
        String loginPassword;
        User authenticatedUser = null;
        do {
            // START main menu
            System.out.println("Welcome to the Camp Application and Management System (CAMs).");
            System.out.println("Select an action: ");
            System.out.println("1: Login");
            System.out.println("0: Quit");
            choice = sc.nextInt();
            switch (choice){
                case 0:
                    System.out.println("Program terminated.");
                    return;
                case 1:
                    authenticatedUser = Login.loginAttempt();
                    break;
            }

            //IF login successful set the menu
            if(authenticatedUser != null){
                if(authenticatedUser instanceof Student)
                    dashboard.setState(new StudentMenuState());
                if(authenticatedUser instanceof Staff)
                    dashboard.setState(new StaffMenuState());
                dashboard.request(); //display menu
            }
        } while(choice != 0);
    }
}
