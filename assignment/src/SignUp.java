import java.util.InputMismatchException;
import java.util.Scanner;

public class SignUp {


    // Initialising scanner
    private static Scanner scanner = new Scanner(System.in);

    public static void signUpMenu(){

        boolean validInput = false;
        String newUsername;
        String newPassword;

        // Prompt the user to enter a username
        System.out.println("Enter a username:");
        String bookName = scanner.nextLine();
        User.setUsername(newUsername);

        // Prompt the user to enter a password
        System.out.println("Enter a password:");
        String bookName = scanner.nextLine();
        User.setPassword(newPassword);

    }

}
