package cams.dashboards;

import cams.users.User;
import cams.database.UnifiedUserRepository;

import java.util.Scanner;

class Login { //Class is package-private
    public static User loginAttempt(){
        Scanner sc = new Scanner(System.in);
        String LoginID;
        String Password;
        User user;

        System.out.println("Enter your User ID:");
        LoginID = sc.next(); // get USER ID
        user = UnifiedUserRepository.getInstance().retrieveUser(LoginID); // check on USER ID
        if(user == null){
            System.out.println("This User ID does not exist, login failed.");
            return null;
        }

        System.out.println("Enter your password: ");
        Password = sc.next(); // get Password
        if(user.validateLogin(Password)){
            System.out.println("Logging in successfully...");
            return user;
        }
        else {
            System.out.println("Wrong password");
        }

        return null;
    }
}
