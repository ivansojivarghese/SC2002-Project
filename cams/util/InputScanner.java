package cams.util;

import java.util.Scanner;


 /**
 * The class Input scanner
 */ 
public class InputScanner {
    private static Scanner scanner;



/** 
 *
 * It is a constructor. Gets instances of mul. scanners - for user input.
 *
 */
    private InputScanner() {} 

    //Prevents multiple scanner instances
    public static Scanner getInstance() {

        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}

