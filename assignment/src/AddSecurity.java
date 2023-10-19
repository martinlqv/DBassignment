import java.util.InputMismatchException;
import java.util.Scanner;

public class AddSecurity {
    // Initialize scanner
    private static Scanner scanner = new Scanner(System.in);

    public static void addSecurityMenu() {

        // Create a new Security object
        Security security = new Security();

        // Get the username of the currently logged-in user from SessionManager
        security.setPortfolioId(DataBaseEngine.getSelectedPortfolioId());

        // Prompt the user to get the security type
        boolean validInput = false;
        int menuNavigation = 0;

        // Continuously prompt user until a valid input is received
        do {
            System.out.println(
                    // Options
                    "---- Select security type ----\n" +
                            "1. Equity\n" +
                            "2. Bond\n" +
                            "3. Derivative"
            );

            try {
                // Get users choice
                menuNavigation = scanner.nextInt();

                // Validate menu choice
                if (menuNavigation >= 1 && menuNavigation <= 3) {
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
        if(menuNavigation == 1) {
            DataBaseEngine.setSelectedInvestmentType("Equity");

        } else if(menuNavigation == 2) {
            DataBaseEngine.setSelectedInvestmentType("Bond");

        } else {
            DataBaseEngine.setSelectedInvestmentType("Derivative");

        }

        DataBaseEngine.selectSecurity();
        security.setTicker(DataBaseEngine.getSelectedTicker());
        security.setInvestmentName(DataBaseEngine.getSelectedInvestmentName());
        security.setInvestmentType(DataBaseEngine.getSelectedInvestmentType());
        security.setRiskLevel(DataBaseEngine.getSelectedRiskLevel());
        security.setInvestmentValue(DataBaseEngine.getSelectedInvestmentValue());

        //Prompt the user to enter quantity
        System.out.println("Enter the quantity:");
        int newQuantity = scanner.nextInt();
        security.setQuantity(newQuantity);


        // Call the method to add the new Portfolio to the database
        DataBaseEngine.addSecurity(security);
    }
}

