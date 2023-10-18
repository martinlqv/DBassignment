import java.util.Scanner;

public class AddSecurity {
    // Initialize scanner
    private static Scanner scaner = new Scanner(System.in);

    public static void addSecurityMenu() {

        // Create a new Security object
        Security security = new Security();

        // Get the username of the currently logged-in user from SessionManager
        security.setPortfolioId(DataBaseEngine.getSelectedPortfolioId());

        // Prompt the user to get



        // Prompt the user to enter a portfolio name
        System.out.println("Enter the ticker:");
        String newTicker = scaner.nextLine();
        security.setTicker(newTicker);

        // Prompt the user to enter the security's name
        System.out.println("Enter the security name:");
        String newSecurityName = scaner.nextLine();
        security.setInvestmentName(newSecurityName);

        // Prompt the user to select an investment type
        System.out.println("Select the type:");
        String newType = scaner.nextLine();
        security.setInvestmentType(newType);

        // Prompt the user to select the risk level
        System.out.println("Select the risk level:");
        String newRiskLevel = scaner.nextLine();
        security.setRiskLevel(newRiskLevel);

        // Prompt the user to enter the investment value
        System.out.println("Enter the investment value:");
        Double newInvestmentValue = scaner.nextDouble();
        security.setInvestmentValue(newInvestmentValue);



        // Call the method to add the new Portfolio to the database
        DataBaseEngine.addSecurity(security);
    }

}
