package cams.dashboards;

import cams.users.User;

import java.util.Scanner;

public class LogoutState implements DashboardState{
    @Override
    public void display(Dashboard dashboard) {
        Scanner sc = new Scanner(System.in);
        String input;
        int option;
        User authenticatedUser = null;

        // START main menu
        System.out.println("Welcome to the Camp Application and Management System (CAMs).");
        System.out.println("1: Login");
        System.out.println("0: Quit");

        while(true) {
            try {
                System.out.print("SELECT AN ACTION: ");
                input = sc.nextLine().strip();;
                option = Integer.parseInt(input);
                if(option >= 0 && option <= 1)
                    break;
                System.out.println("Invalid input, please try again.");
            }
            catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        try {
            switch (option) {
                case 0 -> {
                    System.out.println("Program terminating...");
                    System.out.println("______________________________________________________________");
                    dashboard.setQuit(true);
                    break;
                }
                case 1 -> {
                    System.out.println("Attempting Login...");
                    System.out.println("______________________________________________________________");
                    authenticatedUser = Login.loginAttempt();
                    break;
                }
            }

            //IF login successful set the menu to the loggedIn menu
            //The loggedIn method sets the dashboard according to user role
            if (authenticatedUser != null) {
                dashboard.setAuthenticatedUser(authenticatedUser);
                dashboard.loggedIn();
            }
        } catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
    }
}
