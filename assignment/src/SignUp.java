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

        // Prompt the user to enter their given name
        System.out.println("Enter your given name:");
        String newName = scanner.nextLine();
        newUser.setName(newName);

        // Prompt the user to enter their surname
        System.out.println("Enter your surname:");
        String newSurname = scanner.nextLine();
        newUser.setSurname(newSurname);

        // Prompt the user to enter their email
        System.out.println("Enter your email address");
        String newEmail = scanner.nextLine();
        newUser.setEmail(newEmail);

        // Prompt the user to enter their date of birth
        System.out.println("Enter your date of birth in the format xxxx-xx-xx");
        String newDateOfBirth = scanner.nextLine();
        newUser.setDateOfBirth(newDateOfBirth);



        DataBaseEngine.addUser(newUser);



    }


}
