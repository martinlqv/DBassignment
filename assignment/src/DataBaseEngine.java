import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;


public class DataBaseEngine {
    // Static Connection object to hold the database connection
    static Connection connection = null;

    // Method to establish a database connection
    public static void establishConnection() {
        try {

            // Load and register the MariaDB JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Driver setup complete");

            // Define the URL for the database connection
            String url = "jdbc:mariadb://atlantis.informatik.umu.se/frih2310_db_ht2023";

            // Retrieve admin username from the Admin class
            Admin myadmin = new Admin();
            String user = myadmin.getAdmin();

            // Retrieve admin password from the Password class
            Password mypassword = new Password();
            String password = mypassword.getPassword();

            try {
                // Attempt to establish a connection to the database
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connection successful");
            } catch (SQLException ex) {
                // Handle any SQL exceptions during the connection process
                System.err.println("Database connection failed: " + ex.toString());
            }

        } catch (ClassNotFoundException e) {
            // Handle ClassNotFound exception if JDBC driver is not found
            System.err.println("Error, class not found: ");
            e.printStackTrace();
        }
    }

    // Method to abort (close) a database connection
    public static void abortConnection() {
        try {
            // Close the database connection
            connection.close();
            System.out.println("Disconnected");
        } catch (SQLException ex) {
            // Handle any SQL exceptions during the disconnect process
            System.err.println("Error: " + ex.toString());
        }
    }

    // Method to print the username of a User object
    public static void printUser(User createdUser) {
        System.out.println(createdUser.getUsername());
    }

    // Method to add a User object to the database
    public static void addUser(User user) {
        // SQL code for inserting a new user, but only if the username does not already exist
        String sqlCode = """
                INSERT INTO Users (username, name, surname, email, password, date_of_birth)
                SELECT ?, ?, ?, ?, ?, ?
                WHERE NOT EXISTS (
                    SELECT 1 FROM Users WHERE username = ?
                );
                """;

        Statement statement = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {
            // Set the parameters for the SQL query
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getDataOfBirth());
            preparedStatement.setString(7, user.getUsername());

            // Execute the SQL query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Handle any SQL exceptions during query execution
            System.err.println("SQL query execution failed: " + e.getMessage());
        } finally {
            // Explicitly close the Statement object if it was opened
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close statement: " + e.getMessage());
                }
            }
        }
    }



    // Method to validate user credentials against the database
    public static boolean validateCredentials(String username, String password) {
        Scanner scanner = new Scanner(System.in);  // Initialize a Scanner object for user input

        while (true) {  // keep asking for credentials until valid entered or user exits

            // select password from Users table where given username matches username
            String sqlQuery = "SELECT password FROM Users WHERE username = ?";
            try (
                    PreparedStatement preparedStatement = DataBaseEngine.connection.prepareStatement(sqlQuery)) {

                // Set the first parameter in the SQL query (the '?' abv) to the given username
                preparedStatement.setString(1, username);
                try (
                        ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Check if the ResultSet has any entries (i.e., if the username exists in the database)
                    if (resultSet.next()) {
                        // Retrieve the password associated with the username from the ResultSet
                        String storedPassword = resultSet.getString("password");
                        // Check if given password matches stored password
                        if (storedPassword.equals(password)) {
                            return true;  // If credentials are valid, exit loop and method, return true
                        }
                    }
                }
            } catch (SQLException e) {
                // Catch any SQL exceptions
                System.err.println("Failed to validate credentials: " + e.getMessage());

                // In case of exception, exit the method, return false
                return false;
            }


        // Prompt user for new username and password if the previous ones were invalid
            System.out.println("Invalid username or password.");
            System.out.println("Do you want to try again? (y/n)");
            String response = scanner.nextLine();
            if ("n".equalsIgnoreCase(response)) {
                return false;  // Exit if the user chooses not to
            }

            // Ask for username and password again
            System.out.println("Enter your username:");
            username = scanner.nextLine();
            System.out.println("Enter your password:");
            password = scanner.nextLine();
        }
    }




    // Method to add portfolio to database
    public static void Addportfolio(Portfolio portfolio) {

        // Define the SQL query to insert a new portfolio, any name.
        String sqlCode = """
    INSERT INTO Portfolios (portfolio_name, description, total_value, username)
    VALUES (?, ?, ?, ?);
    """;


        Statement statement = null;
        // Use try-with-resources to automatically close PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {
            // Set the placeholders in the SQL query with actual values.
            preparedStatement.setString(1, portfolio.getPortfolioName()); // Set portfolio name
            preparedStatement.setString(2, portfolio.getDescription());  // Set description
            preparedStatement.setDouble(3, portfolio.getTotalValue());  // Set total value
            preparedStatement.setString(4, portfolio.getUsername());    // Set username

            // Execute the SQL query to insert the new portfolio
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Catch and display any SQL exception that occurs during query execution
            System.err.println("SQL query execution failed: " + e.getMessage());
        } finally {
            // Explicitly close the Statement object if it was opened
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close statement: " + e.getMessage());
                }
            }
        }
    }




}
