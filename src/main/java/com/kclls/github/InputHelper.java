package com.kclls.github;

import java.io.InputStream;
import java.util.Scanner;

public class InputHelper {
    private final Scanner input;


    /*
     * Default constructor which uses input stream
     */
    public InputHelper() {
        input = new Scanner(System.in);
    }

    /*
     * Overloaded constructor allowing for mock input for testing
     */
    public InputHelper(InputStream in) {
        input = new Scanner(in);
    }

    public String getStringInput() {
        return input.nextLine();
    }

    public int getIntInput() {
        // Loop until valid number to return
        while (true) {
            String str = input.nextLine();
            int res = 0;
            try {
                res = Integer.parseInt(str);
                return res;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again:");
            }
        }
    }
}
