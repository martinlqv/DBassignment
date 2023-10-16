import java.util.Scanner;

public class AddPortfolio {

    // Initialize scanner
    private static Scanner scaner = new Scanner(System.in);

    public static void addPortfolioMenu() {

        // Create a new Portfolio object
        Portfolio portfolio = new Portfolio();

        // Get the username of the currently logged-in user from SessionManager
        String currentUsername = SessionManager.getCurrentUsername();

        // Set the username in the Portfolio object
        portfolio.setUsername(currentUsername);

        // Prompt the user to enter a portfolio name
        System.out.println("Enter a portfolio name:");
        String newPortfolioName = scaner.nextLine();
        portfolio.setPortfolioName(newPortfolioName);

        // Prompt the user to enter a description
        System.out.println("Enter a description:");
        String newDescription = scaner.nextLine();
        portfolio.setDescription(newDescription);


        // THIS IS NOT NEEDED
        // Prompt the user to enter the total value
        /*
        System.out.println("Enter the total value:");
        double newTotalValue = scaner.nextDouble();
        scaner.nextLine();  // Consume the newline character
        Portfolio.setTotalValue(newTotalValue);
        */


        // Call the method to add the new Portfolio to the database
        DataBaseEngine.addPortfolio(portfolio);
    }
}
