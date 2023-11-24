package cams.dashboards;

import cams.users.User;
import cams.util.UserInput;

public class LogoutState implements DashboardState{
    @Override
    public void display(Dashboard dashboard) {
        int option;
        User authenticatedUser = null;

        // START main menu
        System.out.println("Welcome to the Camp Application and Management System (CAMs).");
        System.out.println("1: Login");
        System.out.println("0: Quit");
        option = UserInput.getIntegerInput(0, 1, "SELECT AN ACTION: ");

        try {
            switch (option) {
                case 0 -> {
                    System.out.println("Program terminating...");
                    System.out.println("______________________________________________________________");
                    dashboard.setQuit(true);
                }
                case 1 -> {
                    System.out.println("Attempting Login...");
                    System.out.println("______________________________________________________________");
                    authenticatedUser = Login.loginAttempt();
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
