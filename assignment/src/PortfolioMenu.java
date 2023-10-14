import java.util.Scanner;
import java.util.InputMismatchException;

public class PortfolioMenu {

    // Initialising scanner
    private static Scanner scanner = new Scanner(System.in);

    public static void displayPortfolioMenu() {

        // Declaring and initialising variables that will be used in the next loop to check if the input is valid.
        boolean validInput = false;
        int menuNavigation = 0;

        // Continuously prompting the user until a valid input is received
        do {
            System.out.println(
                    // Menu options
                    "---- Portfolios ----\n" +
                            "1. Create\n" +
                            "2. View\n" +
                            "3. Update\n" +
                            "4. Delete\n" +
                            "5. Exit to Main Menu"
            );
            try {
                // Getting the users menu choice
                menuNavigation = scanner.nextInt();

                // Validating the menu choice
                if (menuNavigation >= 1 && menuNavigation <= 5) {
                    validInput = true;


                } else {
                    System.out.println("Please provide a valid input");
                }

            } catch (InputMismatchException ime) {
                // Handling invalid input e.g., non-integers
                System.out.println("Please enter a valid integer between 1 and 3");
                scanner.nextLine();
            }
        } while (!validInput);



        // Handling user's menu choice
        if(menuNavigation == 1){
            // Call method to create a portfolio
            System.out.println("Under Construction");
            displayPortfolioMenu();


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
            System.out.println("Under Construction");
            displayPortfolioMenu();

        } else if(menuNavigation == 5) {

            MainMenu.displayMenu(); // Returning to the main menu

        } else{

            DataBaseEngine.abortConnection();
            System.exit(0); // Exiting the program
        }


    }

}
