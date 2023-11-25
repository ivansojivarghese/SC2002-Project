package cams.util;

import java.util.Scanner;

public class UserInput {
    //Method to safely get and returns an integer input
    public static int getIntegerInput(int min, int max, String request){
        Scanner sc = new Scanner(System.in);
        String input;
        int option;
        while(true) {
            try {
                System.out.print(request);
                input = sc.nextLine().strip();
                option = Integer.parseInt(input);
                if(option >= min && option <= max)
                    break;
                System.out.println("Invalid input, please try again.");
            }

            catch (Exception e) {
                System.out.println("Invalid input " + e.getMessage().toLowerCase() + ". Please enter an integer.");
            }
        }
        return option;
    }
    
    public static String getStringInput() { // update string-based input
    	String input;
        Scanner sc = new Scanner(System.in);
        input = sc.next();
        return input;
    }

    public static boolean getBoolInput(String request){
        String input;
        Scanner sc = new Scanner(System.in);
        Boolean bool;
        do {
            System.out.println(request);
            input = sc.next();
            bool = UserInput.validateInput(input);
        } while (bool == null);
        return bool;
    }
    public static Boolean validateInput(String bool){
        if ("1".equalsIgnoreCase(bool) || "yes".equalsIgnoreCase(bool) || bool.equalsIgnoreCase("y") ||
                "true".equalsIgnoreCase(bool)) {
            return true;
        }
        else if ("0".equalsIgnoreCase(bool) || "no".equalsIgnoreCase(bool) ||
                "false".equalsIgnoreCase(bool) || bool.equalsIgnoreCase("n")) {
            return false;
        }
        else
            return null;
    }
}
