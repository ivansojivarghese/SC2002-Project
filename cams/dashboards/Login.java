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
        sc.nextLine(); //consume new line
        if(user.validateLogin(password)){
            System.out.println("Logging in successfully...");
        }
        else {
            System.out.println("Wrong password! Please try again.");
            return null;
        }

        if(password.equalsIgnoreCase("password")) {
            //If user is logging in for the first time (i.e. if password == password), prompt to change password
            System.out.println("You are using the default password.");
            while (true) {
                System.out.print("Please enter new password (Case Sensitive): ");
                password = sc.nextLine();
                if((password.equalsIgnoreCase("password") || password.isBlank() || password.length() < 8))
                    System.out.println("Password must not be blank and must have at least 8 characters.");
                else{
                    break;
                }
            }
            user.setPassword(password);
            System.out.println("Password successfully changed!");
        }

        return user;
    }
}
