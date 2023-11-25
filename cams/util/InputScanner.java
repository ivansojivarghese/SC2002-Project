package cams.util;

import java.util.Scanner;

public class InputScanner {
    private static Scanner scanner;

    private InputScanner() {}

    //Prevents multiple scanner instances
    public static Scanner getInstance() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}

