import java.util.Scanner;

public class LogIn {
    private static Scanner scaner = new Scanner(System.in);

    public static void logInMenu() {
        System.out.println("Enter your username:");
        String username = scaner.nextLine();

        System.out.println("Enter your password:");
        String password = scaner.nextLine();

        // Call some method to validate the credentials
        if (DataBaseEngine.validateCredentials(username, password)) {
            System.out.println("Login successful!");
            PortfolioMenu.displayPortfolioMenu();  // Display the portfolio menu upon successful login
            //return true;
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}
