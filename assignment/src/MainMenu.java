import java.util.Scanner;
import java.util.InputMismatchException;

public class MainMenu {

    // Initialising scanner
    private static Scanner scanner = new Scanner(System.in);

    public static void displayMenu() {

        // Declaring and initialising variables that will be used in the next loop to check if the input is valid.
        boolean validInput = false;
        int menuNavigation = 0;

        // Continuously prompting the user until a valid input is received
        do {
            System.out.println(
                    // Menu options
                    "Welcome to Portfolio Manager\n" +
                            "1. Log in\n" +
                            "2. Sign up\n" +
                            "3. Exit"
            );
            try {
                // Getting the users menu choice
                menuNavigation = scanner.nextInt();

                // Validating the menu choice
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
        if(menuNavigation == 1){
            //LogIn.logInMenu(); // Displaying all the books
            displayMenu(); // Returning to the main menu
        } else if(menuNavigation == 2) {
            SignUp.signUpMenu(); // Adding a new book
            displayMenu(); // Returning to the main menu
        } else{
            System.exit(0); // Exiting the program
        }


    }

}
