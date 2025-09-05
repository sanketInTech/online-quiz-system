package util;

import java.util.Scanner;
import java.util.regex.Pattern;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^.{6,50}$");

    private InputUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Gets a non-empty string input from the user
     * @param prompt The prompt to display
     * @return The non-empty string input
     */
    public static String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: Input cannot be empty. Please try again.");
        }
    }

    /**
     * Gets a valid integer input within a specified range
     * @param prompt The prompt to display
     * @param min The minimum allowed value (inclusive)
     * @param max The maximum allowed value (inclusive)
     * @return The valid integer input
     */
    public static int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                
                if (value < min || value > max) {
                    System.out.printf("Error: Please enter a number between %d and %d.\n", min, max);
                    continue;
                }
                
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    /**
     * Gets a yes/no confirmation from the user
     * @param prompt The prompt to display (should end with (Y/N)?)
     * @return true if user enters Y/y, false if N/n
     */
    public static boolean getYesNoInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            }
            
            System.out.println("Please enter Y/Yes or N/No.");
        }
    }

    /**
     * Gets a single character input (A-D)
     * @param prompt The prompt to display
     * @return The uppercase character (A-D)
     */
    public static char getCharInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            
            if (input.length() == 1 && input.matches("[A-D]")) {
                return input.charAt(0);
            }
            
            System.out.println("Error: Please enter a single character from A to D.");
        }
    }

    /**
     * Gets a password input with validation
     * @param prompt The prompt to display
     * @return The password string
     */
    public static String getPassword(String prompt) {
        while (true) {
            System.out.print(prompt);
            String password;
            
            if (System.console() != null) {
                password = new String(System.console().readPassword());
            } else {
                // Fallback for IDEs that don't support System.console()
                password = scanner.nextLine().trim();
            }
            
            if (PASSWORD_PATTERN.matcher(password).matches()) {
                return password;
            }
            
            System.out.println("Error: Password must be between 6 and 50 characters long.");
        }
    }
    
    /**
     * Gets a valid username input
     * @param prompt The prompt to display
     * @return A valid username string
     */
    public static String getUsername(String prompt) {
        while (true) {
            String username = getStringInput(prompt);
            
            if (USERNAME_PATTERN.matcher(username).matches()) {
                return username;
            }
            
            System.out.println("Error: Username must be 3-20 characters long and can only contain letters, numbers, and underscores.");
        }
    }
}
