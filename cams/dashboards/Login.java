package cams.dashboards;

import cams.users.User;
import cams.database.UnifiedUserRepository;

import java.util.Scanner;

class Login { //Class is package-private
    public static User loginAttempt(){
        Scanner sc = new Scanner(System.in);
        String LoginID;
        String password;
        User user;

        System.out.println("                            LOGIN                             ");
        System.out.print("Enter your User ID: ");
        LoginID = sc.next(); // get USER ID

        user = UnifiedUserRepository.getInstance().retrieveUser(LoginID); // check on USER ID
        if(user == null){
            System.out.println("This User ID does not exist, login failed.");
            return null;
        }

        System.out.print("Enter your password (Case Sensitive): ");
        password = sc.next(); // get Password
        if(user.validateLogin(password)){
            System.out.println("Logging in successfully...");
        }
        else {
            System.out.println("Wrong password! Please try again.");
            return null;
        }

        //If user is logging in for the first time (i.e. if password == password), prompt to change password
        do{
            System.out.println("You are using the default password.");
            System.out.println("Please enter new password (Case Sensitive):");
            password = sc.nextLine().strip();
        } while(password.equalsIgnoreCase("password"));

        user.setPassword(password);
        System.out.println("Password successfully changed!");

        return user;
    }
}
