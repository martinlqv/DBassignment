public class SessionManager {

    // Static var holds the username of the currently logged in user
    private static String currentUsername;

    // Static var holds the ticker of the currently selected security
    private static String currentTicker;

    // Method to set the current username
    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }

    // Method to get the current username
    public static String getCurrentUsername() {
        return currentUsername;
    }

    // Method to log out the current user (clear username)
    public static void logOut() {
        currentUsername = null;
        currentTicker = null;  // Optionally clear the ticker when logging out
    }

    // Method to set the current ticker
    public static void setCurrentTicker(String ticker) {
        currentTicker = ticker;
    }

    // Method to get the current ticker
    public static String getCurrentTicker() {
        return currentTicker;
    }

    // Method to clear the current ticker (optional)
    public static void clearCurrentTicker() {
        currentTicker = null;
    }
}
