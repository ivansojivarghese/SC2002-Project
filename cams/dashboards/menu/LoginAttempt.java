package cams.dashboards.menu;

import cams.users.User;
import cams.database.UnifiedUserRepository;
import cams.util.InputScanner;

import java.util.Scanner;

/**
 * Each instance of the class represents a login attempt
 * and handles the login process for users.
 * This class is responsible for prompting the user for login credentials
 * and initiating the validation method encapsulated in each {@link User} object.
 * It detects and handles password changes for a first-time successful login attempt.
 */
class LoginAttempt { //Class is package-private
    /**
     * Attempts to log in a user with a given user ID and password.
     * <p>
     * This method prompts the user for their User ID and password, checks these credentials,
     * and handles the login process, including a first-time login check that prompts for password change.
     *
     * @return The {@link User} object if login is successful; null otherwise.
     */
    public User loginAttempt(){
        Scanner sc = InputScanner.getInstance();
        String LoginID;
        String password;
        User user;

        System.out.println("                            LOGIN                             ");
        System.out.print("Enter your User ID: ");
        LoginID = sc.nextLine(); // get USER ID

        user = UnifiedUserRepository.getInstance().retrieveUser(LoginID); // check on USER ID
        if(user == null){
            System.out.println("This User ID does not exist, login failed.");
            return null;
        }

        System.out.print("Enter your password (Case Sensitive): ");
        password = sc.nextLine(); // get Password
        if(user.validateLogin(password)){
            System.out.println("Logging in successfully...");
        }
        else {
            System.out.println("Wrong password! Please try again.");
            return null;
        }

        // Prompt for password change if using default password
        if (password.equalsIgnoreCase("password")) {
            System.out.println("You are using the default password.");
            changeDefaultPassword(user, sc);
            System.out.println("Please log in again.");
            return null;
        }

        return user;
    }

    /**
     * Assists the user in changing their default password.
     *
     * @param user The {@link User} who is changing their password.
     * @param sc   The {@link Scanner} instance for reading user input.
     */
    private static void changeDefaultPassword(User user, Scanner sc) {
        String password;
        while (true) {
            System.out.print("Please enter new password (Case Sensitive): ");
            password = sc.nextLine();

            if (password.equalsIgnoreCase("password")) {
                System.out.println("For security reasons you cannot change your password to the default password.");
            } else if (password.isBlank() || password.length() < 8) {
                System.out.println("Password must not be blank and must have at least 8 characters.");
            } else {
                // Valid password, exit the loop
                break;
            }
        }
        user.setPassword(password);
        System.out.println("Password successfully changed!");
    }

}

