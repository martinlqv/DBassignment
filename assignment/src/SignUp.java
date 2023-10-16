import java.util.InputMismatchException;
import java.util.Scanner;

public class SignUp {


    // Initialising scanner
    private static Scanner scanner = new Scanner(System.in);

    // Check for email validity
    private static boolean isValidEmail(String email) {
        String regex = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@" +
                "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
        return email.matches(regex);
    }

    // Check for valid date of birth format
    private static boolean isValidDateOfBirth(String dob) {
        String regex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
        return dob.matches(regex);
    }




    public static void signUpMenu(){



        User newUser = new User();


        while (true) { // Loop until a valid and unique username is entered
            System.out.println("Enter a username:");
            String newUsername = scanner.nextLine();

            if (newUsername.length() < 3 || newUsername.length() > 50) {
                System.out.println("Error: Username must be between 3 and 50 characters long.");
                continue; // Go to the next iteration of the loop
            }

            if (DataBaseEngine.usernameExists(newUsername)) {
                System.out.println("Error: Username already exists. Please choose another.");
                continue; // Go to the next iteration of the loop
            }

            newUser.setUsername(newUsername);
            // At this point, the username is both valid and unique
            break; // Exit the loop
        }

        // Prompt the user to enter a password
        while (true) {
            System.out.println("Enter a password:");
            String newPassword = scanner.nextLine();

            if (newPassword.length() < 5 || newPassword.length() > 100) {
                System.out.println("Error: Password must be between 5 and 100 characters long.");
                continue;
            }

            newUser.setPassword(newPassword);
            break;
        }

        // Prompt the user to enter their name
        while (true) {
            System.out.println("Enter your given name:");
            String newName = scanner.nextLine();

            if (newName.length() < 1 || newName.length() > 50) {
                System.out.println("You're name must be at least one character- and less than 50 characters long. " +
                        "Please contact your closest government to change it.");
                continue;
            }

            newUser.setName(newName);
            break; // Exit the loop
        }

        // Prompt the user to enter their surname
        while (true) {
            System.out.println("Enter your surname:");
            String newSurname = scanner.nextLine();

            if (newSurname.length() < 1 || newSurname.length() > 100) {
                System.out.println("You're surname must be at least one character- and less than 100 characters " +
                        "long. Please contact your closest government to change it.");
                continue;
            }

            newUser.setSurname(newSurname);
            break;
        }

        // Prompt the user to enter their email
        while (true) {
            System.out.println("Enter your email address:");
            String newEmail = scanner.nextLine();

            if (!isValidEmail(newEmail)) {
                System.out.println("Invalid email address. Ensure it has a valid format.");
                continue;
            }

            newUser.setEmail(newEmail);
            break;
        }

        // Prompt the user to enter their date of birth
        while (true) {
            System.out.println("Enter your date of birth in the format xxxx-xx-xx:");
            String newDateOfBirth = scanner.nextLine();

            if (!isValidDateOfBirth(newDateOfBirth)) {
                System.out.println("Invalid date format. Ensure it follows the xxxx-xx-xx format.");
                continue;
            }

            newUser.setDateOfBirth(newDateOfBirth);
            break;
        }


        DataBaseEngine.addUser(newUser);



    }


}
