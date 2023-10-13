import java.util.InputMismatchException;
import java.util.Scanner;

public class SignUp {


    // Initialising scanner
    private static Scanner scanner = new Scanner(System.in);

    public static void signUpMenu(){


        User newUser = new User();

        // Prompt the user to enter a username
        System.out.println("Enter a username:");
        String newUsername = scanner.nextLine();
        newUser.setUsername(newUsername);

        // Prompt the user to enter a password
        System.out.println("Enter a password:");
        String newPassword = scanner.nextLine();
        newUser.setPassword(newPassword);

    }


}
