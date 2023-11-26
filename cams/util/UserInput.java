package cams.util;

import java.util.Scanner;


 /**
 * The class User input from scanner
 */ 
public class UserInput {
    //Method to safely get and returns an integer input

/** 
 *
 * Gets the integer input. ONLY integers allowed. Returns error if not.
 *
 * @param min  the min. 
 * @param max  the max. 
 * @param request  the request. 
 * @return the integer input
 */
    public static int getIntegerInput(int min, int max, String request) { 

        Scanner sc = InputScanner.getInstance();
        int option = min - 1; // Initialize to an invalid value
        while (true) {
            try {
                System.out.print(request);
                String input = sc.nextLine().trim(); // Use trim to remove leading and trailing spaces

                // Attempt to parse the input as an integer
                option = Integer.parseInt(input);

                // Check if the number is within the specified range
                if (option >= min && option <= max) {
                    return option; // Valid input, return the parsed number
                } else {
                    System.out.println("Input must be from " + min + " to " + max + ".");
                }
            } catch (NumberFormatException e) {
                // Catch only NumberFormatException for invalid integer input
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }
    


/** 
 *
 * Gets the string input
 *
 * @return the string input
 */
    public static String getStringInput() { // update string-based input 
    	String input;
        Scanner sc = InputScanner.getInstance();
        input = sc.nextLine();
        return input;
    }


    public static boolean getBoolInput(String request){

        String input;
        Scanner sc = InputScanner.getInstance();
        Boolean bool;
        do {
            System.out.println(request);
            input = sc.nextLine();
            bool = UserInput.validateInput(input);
        } while (bool == null);
        return bool;
    }

/** 
 *
 * Validate input
 *
 * @param bool  the boolean. Provides 2-values determinants to questions (during registration, etc.). Support for Yes/No questions.
 * @return Boolean
 */
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
