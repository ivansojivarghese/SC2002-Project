package cams.dashboards.menu;

import cams.dashboards.Dashboard;
import cams.dashboards.DashboardState;
import cams.users.User;
import cams.util.InputScanner;

import java.util.Scanner;

/**
 * Defines a template base class for user menus
 * and provides the common change password method
 */
public abstract class UserUI implements DashboardState {
    /**
     * Displays information about the user on the dashboard.
     *
     * @param user The user whose information is to be displayed.
     */
    abstract protected void userInfo(User user);

    /**
     * Common method for interacting with the user to change the password
     * @param user The user whose password is to be changed
     */
    protected void changePassword(User user, Dashboard dashboard){
        Scanner sc = InputScanner.getInstance();

        System.out.println("\nEnter new password (Case Sensitive): ");
        String newPassword = sc.nextLine();

        // Failure conditions
        if(newPassword.equalsIgnoreCase("password")){
            System.out.println("For security reasons you cannot change your password to the default password.");
            return;
        }
        if(newPassword.isBlank() || newPassword.length() < 8) {
            System.out.println("Password must not be blank and must have at least 8 characters.");
            return;
        }
        if(newPassword.equalsIgnoreCase(user.getPassword())){
            System.out.println("You cannot change to the same password!");
            return;
        }
        //Success
        user.setPassword(newPassword);
        System.out.println("Password successfully changed!");
        System.out.println("Please log in again.");
        dashboard.logout();
    }
}
