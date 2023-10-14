public class SessionManager {
    // Static variable to hold the username of the current logged-in user
    private static String currentUsername;

    // Method to set the current username
    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }

    // Method to get the current username
    public static String getCurrentUsername() {
        return currentUsername;
    }

    // Method to log out the current user (clear the username)
    public static void logOut() {
        currentUsername = null;
    }
}
