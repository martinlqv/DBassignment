import javax.xml.crypto.Data;
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
                    "\n---- Portfolios ----\n" +
                            "1. View\n" +
                            "2. Select\n" +
                            "3. Create\n" +
                            //"4. Delete\n" +
                            "4. Exit to Main Menu"
            );

            try {
                // Get users choice
                menuNavigation = scanner.nextInt();

                // Validate menu choice
                if (menuNavigation >= 1 && menuNavigation <= 4) {
                    validInput = true;


                } else {
                    System.out.println("Please provide a valid input");
                }

            } catch (InputMismatchException ime) {
                // Handling invalid input e.g., non-integers
                System.out.println("Please enter a valid integer between 1 and 4");
                scanner.nextLine();
            }
        } while (!validInput);



        // Handling user's menu choice
        if(menuNavigation == 1){

            DataBaseEngine.viewPortfolios();
            displayPortfolioMenu();


        } else if(menuNavigation == 2) {
            boolean isPortfolioSelected = DataBaseEngine.selectPortfolio();
            if (isPortfolioSelected) {
                functionMenu();
                displayPortfolioMenu();

            } else {

                System.out.println("Could not proceed as no valid portfolio was selected.");
                // Maybe return to the main menu or provide other options
                displayPortfolioMenu();
            }

      } else if(menuNavigation == 3) {

            // Call method to create a portfolio
            AddPortfolio.addPortfolioMenu();
            displayPortfolioMenu(); //Maybe sub this for immediate portfolio view, and add Assetmenu

        } else if(menuNavigation == 4) {

            MainMenu.displayMenu(); // Returning to the main menu

        } else{

            DataBaseEngine.abortConnection();
            System.exit(0); // Exiting the program
        }

    }


    private static void functionMenu() {
        // Declaring and initialising the vars used in the next loop to check if input valid.
        boolean validInput = false;
        int menuNavigation = 0;

        // Continuously prompt user until a valid input is received
        do {
            System.out.println("\n\nSelected Portfolio ID: " + DataBaseEngine.getSelectedPortfolioId());
            System.out.println("Selected Portfolio Name: " + DataBaseEngine.getSelectedPortfolioName());
            System.out.println(
                    // Options
                    "----------------\n" +
                            "1. View\n" +
                            "2. Add security\n" +
                            "3. Update quantity\n" +
                            "4. Delete security\n" +
                            "5. Delete portfolio\n" +
                            "6. Back"
            );

            try {
                // Get users choice
                menuNavigation = scanner.nextInt();

                // Validate menu choice
                if (menuNavigation >= 1 && menuNavigation <= 6) {
                    validInput = true;


                } else {
                    System.out.println("Please provide a valid input");
                }

            } catch (InputMismatchException ime) {
                // Handling invalid input e.g., non-integers
                System.out.println("Please enter a valid integer between 1 and 6");
                scanner.nextLine();
            }
        } while (!validInput);

        // Handling user's menu choice
        if(menuNavigation == 1){
            DataBaseEngine.viewSecurities();
            functionMenu();

        } else if(menuNavigation == 2) {
            // Call method to view portfolios
            AddSecurity.addSecurityMenu();
            functionMenu();


        } else if(menuNavigation == 3) {

            // Call method to update a portfolio
            DataBaseEngine.updateSecurity();
            functionMenu();




        } else if(menuNavigation == 4) {

            // Call method to delete a portfolio
            DataBaseEngine.deleteSecurity();
            functionMenu();

        } else if(menuNavigation == 5) {

            DataBaseEngine.deletePortfolio();
            displayPortfolioMenu(); // Returning to the main menu

        } else{

            displayPortfolioMenu();

        }

    }




}
