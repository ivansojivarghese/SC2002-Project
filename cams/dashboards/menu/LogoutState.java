package cams.dashboards.menu;

import cams.users.User;
import cams.util.UserInput;
/**
 * Represents the state of the dashboard when a user is not logged in.
 * This state displays the main menu options such as LoginAttempt and Quit.
 * Implements {@link DashboardState} for managing the display of this state.
 */
public class LogoutState implements DashboardState{
    /**
     * Displays the main menu options in the logout state.
     * <p>
     * In this state, the user can choose to either log in or quit the application.
     * If the user chooses to log in, it attempts to authenticate the user.
     *
     * @param dashboard The {@link Dashboard} context in which this state operates.
     */
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
                    authenticatedUser = new LoginAttempt().loginAttempt();
                }
            }

            // Check if login was successful and update dashboard state
            // Set the current user owning the dashboard
            if (authenticatedUser != null) {
                dashboard.setAuthenticatedUser(authenticatedUser);
                dashboard.loggedIn();
            }
            else
                return;
        } catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
    }
}
