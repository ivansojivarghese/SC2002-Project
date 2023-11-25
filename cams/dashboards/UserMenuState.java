package cams.dashboards;

import cams.users.User;
import cams.util.InputScanner;

import java.util.Scanner;

public abstract class UserMenuState implements DashboardState {
    protected void changePassword(User user){
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
        //Success
        user.setPassword(newPassword);
        System.out.println("Password successfully changed!");
    }
}
