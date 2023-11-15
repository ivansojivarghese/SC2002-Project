package cams;

import java.util.Scanner;

public class StaffMenuState implements DashboardState {
    @Override
    public void display(Dashboard dashboard) {
        int option;
        Scanner sc = new Scanner(System.in);
        User user = dashboard.getAuthenticatedUser();

        // Code to display options
        System.out.println("-- DASHBOARD --");
        System.out.println("STAFF Username: " + user.getUserID() + ", Faculty: " + user.getFaculty());
        System.out.println("-- OPTIONS --");
        System.out.println("(1) Change your password");
        System.out.println("(2) Logout");
        System.out.println("(3) View my Camps");

        if (user instanceof Staff) {
            System.out.println("(4) View all Camps");
            System.out.println("(5) Edit your Camp(s)");
            System.out.println("(6) Create a new Camp");
        }
        option = sc.nextInt();

        switch (option){
            case 1:
                String newPassword;

                System.out.println("Enter new password:");
                newPassword = sc.next();
                user.setPassword(newPassword);
                System.out.println("Password successfully changed!");
                break;

            case 2:
                dashboard.logout();

            case 3:
                user.displayMyCamps();

            case 4:
                user.viewAllCamps(); //maybe put this method for viewing available camps in camp repo instead
                break;

            case 5:
                user.viewAllCamps();

        }
    }
}
