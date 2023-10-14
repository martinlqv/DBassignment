import java.util.Scanner;
import java.util.InputMismatchException;

public class PortfolioMenu {

    // Initialising scanner
    private static Scanner scanner = new Scanner(System.in);

    public static void displayPortfolioMenu() {

        // Declaring and initialising the vars used in the next loop to check if input valid.
        boolean validInput = false;
        int menuNavigation = 0;

        // Continuously prompt user until a valid input is received
        do {
            System.out.println(
                    // Options
                    "---- Portfolios ----\n" +
                            "1. Create\n" +
                            "2. View\n" +
                            "3. Update\n" +
                            "4. Delete\n" +
                            "5. Exit to Main Menu"
            );

            try {
                // Get users choice
                menuNavigation = scanner.nextInt();

                // Validate menu choice
                if (menuNavigation >= 1 && menuNavigation <= 5) {
                    validInput = true;


                } else {
                    System.out.println("Please provide a valid input");
                }

            } catch (InputMismatchException ime) {
                // Handling invalid input e.g., non-integers
                System.out.println("Please enter a valid integer between 1 and 5");
                scanner.nextLine();
            }
        } while (!validInput);



        // Handling user's menu choice
        if(menuNavigation == 1){

            // Call method to create a portfolio
            Addportfolio.addPortfolioMenu();
            displayPortfolioMenu(); //Maybe sub this for immediate portfolio view, and add Assetmenu

        } else if(menuNavigation == 2) {
            // Call method to view portfolios
            System.out.println("Under Construction");

            displayPortfolioMenu();

        } else if(menuNavigation == 3) {

            // Call method to update a portfolio
            System.out.println("Under Construction");
            displayPortfolioMenu();


        } else if(menuNavigation == 4) {

            // Call method to delete a portfolio
            String currentUsername = SessionManager.getCurrentUsername();
            System.out.println("TEST: Logged in: " + currentUsername);

            displayPortfolioMenu();

        } else if(menuNavigation == 5) {

            MainMenu.displayMenu(); // Returning to the main menu

        } else{

            DataBaseEngine.abortConnection();
            System.exit(0); // Exiting the program
        }

    }

}
