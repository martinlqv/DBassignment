import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.*;


public class DataBaseEngine {
    // Static Connection object to hold the database connection
    private static Connection connection = null;
    private static String selectedPortfolioId;
    private static String selectedPortfolioName;
    private static String selectedTicker;
    private static String selectedInvestmentName;
    private static String selectedInvestmentType;
    private static String selectedRiskLevel;
    private static Double selectedInvestmentValue;

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


    // Method to add a User object to the database ---------------------------------------------------------------------
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



    // Method to validate user credentials against the database --------------------------------------------------------
    public static boolean validateCredentials(String username, String password) {
        Scanner scanner = new Scanner(System.in);  // Initialize a Scanner object for user input

        while (true) {  // keep asking for credentials until valid entered or user exits

            // select password from Users table where given username matches username
            String sqlQuery = "SELECT password FROM Users WHERE username = ?";
            try (
                    PreparedStatement preparedStatement = DataBaseEngine.connection.prepareStatement(sqlQuery)) {

                // Set the first parameter in query (the '?' abv) to the given username
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
                // Catch exceptions
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




    // Method to add portfolio to database -----------------------------------------------------------------------------
    public static void addPortfolio(Portfolio portfolio) {

        // Define the SQL query to insert a new portfolio, any name.
        String sqlCode = """
                INSERT INTO Portfolios (portfolio_name, description, username)
                VALUES (?, ?, ?);
    """;


        Statement statement = null;
        // Use try-with-resources to automatically close PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {
            // Set the placeholders in the SQL query with actual values.
            preparedStatement.setString(1, portfolio.getPortfolioName()); // Set portfolio name
            preparedStatement.setString(2, portfolio.getDescription());  // Set description
            //preparedStatement.setDouble(3, portfolio.getTotalValue());  // Set total value
            preparedStatement.setString(3, portfolio.getUsername());    // Set username

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


    public static boolean usernameExists(String username) {
        String query = "SELECT 1 FROM Users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true; // username already exists
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false; // username does not exist
    }




    // Method to select portfolio --------------------------------------------------------------------------------------
    public static void selectPortfolio() {
        String sqlCode = """
        SELECT portfolio_id, portfolio_name
        FROM Portfolios
        WHERE username = ? ;
        """;

        List<String> portfolioIds = new ArrayList<>();
        Map<String, String> portfolioNames = new HashMap<>(); // Map to associate each ID with its name

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {
            preparedStatement.setString(1, SessionManager.getCurrentUsername());
            ResultSet resultSet = preparedStatement.executeQuery();

            int counter = 1;
            while (resultSet.next()) {
                String portfolioId = resultSet.getString("portfolio_id");
                String portfolioName = resultSet.getString("portfolio_name");

                portfolioIds.add(portfolioId);
                portfolioNames.put(portfolioId, portfolioName); // Store the name associated with the ID
                //removed portfolio id from printout, looks confusing. Also displayed upon select = redundant.
                System.out.println(counter + ". " + portfolioName);
                counter++;
            }

            if (portfolioIds.isEmpty()) {
                System.out.println("No portfolios found for the user.");
                return;
            }

            System.out.println("Enter the number of the portfolio you want to update:");
            Scanner scanner = new Scanner(System.in);

            int selectedNumber = -1;
            while (selectedNumber < 1 || selectedNumber > portfolioIds.size()) {
                try {
                    selectedNumber = scanner.nextInt();
                    if (selectedNumber < 1 || selectedNumber > portfolioIds.size()) {
                        System.out.println("Invalid number. Please enter a number between 1 and " + portfolioIds.size());
                    }
                } catch (InputMismatchException ime) {
                    System.out.println("Please enter a valid number.");
                    scanner.nextLine(); // Clearing the invalid input
                }
            }

            selectedPortfolioId = portfolioIds.get(selectedNumber - 1);
            selectedPortfolioName = portfolioNames.get(selectedPortfolioId); // Get the name using the selected ID

        } catch (SQLException e) {
            System.err.println("SQL query execution failed: " + e.getMessage());
        }
    }

    // Getter methods to fetch the selected values
    public static int getSelectedPortfolioId() {
        return Integer.parseInt(selectedPortfolioId);
    }

    public static String getSelectedPortfolioName() {
        return selectedPortfolioName;
    }







    // Add security ---------------------------------------------------------------------------------------------------
    /*public static void addSecurity(Security security) {

        // Define the SQL query to insert a new portfolio, any name.
        String sqlCode = """
                INSERT INTO Investment_products (ticker, investment_name, investment_type, risk_level,
                investment_value)
                VALUES (?, ?, ?, ?, ?);
    """;


        Statement statement = null;
        // Use try-with-resources to automatically close PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {
            // Set the placeholders in the SQL query with actual values.
            preparedStatement.setString(1, security.getTicker());
            preparedStatement.setString(2, security.getInvestmentName());
            preparedStatement.setString(3, security.getInvestmentType());
            preparedStatement.setString(4, security.getRiskLevel());
            preparedStatement.setDouble(5, security.getInvestmentValue());
            //preparedStatement.setDouble(6, security.getPortfolioId());

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
*/

    // View selected portfolio -----------------------------------------------------------------------------------

    // IMPLEMENTED THIS AS VIEWSECURITIES
    /*
    public static void viewAssets() {
        String sqlCode = """
                SELECT ticker, investment_name, investment_type, risk_level, investment_value
                FROM Investment_products;
                """;

        List<String> tickers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {
            preparedStatement.setString(1, selectedPortfolioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String ticker = resultSet.getString("ticker");
                tickers.add(ticker);
                String investmentName = resultSet.getString("investment_name");
                String investmentType = resultSet.getString("investment_type");
                String riskLevel = resultSet.getString("risk_level");
                double investmentValue = resultSet.getDouble("investment_value");

                System.out.println(ticker + " " + investmentName + " " + investmentType + " " + riskLevel
                        + " " + investmentValue);
            }

            if (tickers.isEmpty()) {
                System.out.println("No securities found for the selected portfolio.");
            }

        } catch (SQLException e) {
            System.err.println("SQL query execution failed: " + e.getMessage());
        }
    }
*/



    // Method to select equity -----------------------------------------------------------------------------------------
    public static void selectSecurity() {
        String sqlCode = """
            SELECT ticker, investment_name, investment_type, risk_level, investment_value
            FROM Investment_products
            WHERE investment_type = ? ;
            """;

        List<String> tickers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {
            preparedStatement.setString(1, selectedInvestmentType);
            ResultSet resultSet = preparedStatement.executeQuery();

            int counter = 1;
            while (resultSet.next()) {
                String ticker = resultSet.getString("ticker");
                tickers.add(ticker);

                System.out.println(counter + ". " + ticker + " | " +
                        resultSet.getString("investment_name") + " | " +
                        resultSet.getString("investment_type") + " | " +
                        resultSet.getString("risk_level") + " | " +
                        resultSet.getDouble("investment_value"));
                counter++;
            }

            if (tickers.isEmpty()) {
                System.out.println("No securities found.");
                return;
            }

            System.out.println("Enter the number of the security you want to select:");
            Scanner scanner = new Scanner(System.in);

            int selectedNumber = -1;
            while (selectedNumber < 1 || selectedNumber > tickers.size()) {
                try {
                    selectedNumber = scanner.nextInt();
                    if (selectedNumber < 1 || selectedNumber > tickers.size()) {
                        System.out.println("Invalid number. Please enter a number between 1 and " + tickers.size());
                    }
                } catch (InputMismatchException ime) {
                    System.out.println("Please enter a valid number.");
                    scanner.nextLine(); // Clearing the invalid input
                }
            }

            resultSet.absolute(selectedNumber); // Move the cursor to the selected row
            selectedTicker = resultSet.getString("ticker");
            selectedInvestmentName = resultSet.getString("investment_name");
            selectedInvestmentType = resultSet.getString("investment_type");
            selectedRiskLevel = resultSet.getString("risk_level");
            selectedInvestmentValue = resultSet.getDouble("investment_value");
        } catch (SQLException e) {
            System.err.println("SQL query execution failed: " + e.getMessage());
        }
    }


    public static void setSelectedInvestmentType(String investmentType) {
        selectedInvestmentType = investmentType;
    }

    public static String getSelectedTicker() {
        return selectedTicker;
    }

    public static String getSelectedInvestmentName() {
        return selectedInvestmentName;
    }

    public static String getSelectedInvestmentType() {
        return selectedInvestmentType;
    }

    public static String getSelectedRiskLevel() {
        return selectedRiskLevel;
    }

    public static Double getSelectedInvestmentValue() {
        return selectedInvestmentValue;
    }




    // Add security ---------------------------------------------------------------------------------------------------

    public static void addSecurity(Security security) {

        //  insert a new portfolio, any name.
        String sqlCode = """
                INSERT INTO Product_quantity (ticker, portfolio_id, quantity)
                VALUES (?, ?, ?);
                """;


        Statement statement = null;
        // Use try-with-resources to automatically close PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {

            // Set the placeholders with actual values.
            preparedStatement.setString(1, security.getTicker());
            preparedStatement.setString(2, selectedPortfolioId);
            preparedStatement.setInt(3, security.getQuantity());

            // Execute
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Catch and display exception that occurs during execution
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



    // Method to view securities
    public static void viewSecurities() {
        int currentPortfolioID = DataBaseEngine.getSelectedPortfolioId();  // Get the current portfolio ID for the user
        String sqlCode = """
        SELECT * FROM Investment_Products_Quantity_View
        WHERE portfolio_id = ?;
        """;


        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {
            preparedStatement.setInt(1, currentPortfolioID);  // Set the portfolio ID parameter

            ResultSet resultSet = preparedStatement.executeQuery();

            // Print column names as the header of the table
            System.out.printf("%-10s %-40s %-15s %-12s %-12s %-10s\n",
                    "Ticker", "Name", "Type", "Risk Level", "Value",  "Quantity");

            // Print a line under the header
            System.out.println("--------- ---------------------------------------- --------------- ------------ ------------ -----------");

            // Print each row in a formatted manner
            while (resultSet.next()) {
                String ticker = resultSet.getString("ticker");
                String investment_name = resultSet.getString("investment_name");
                String investment_type = resultSet.getString("investment_type");
                String risk_level = resultSet.getString("risk_level");
                double investment_value = resultSet.getDouble("investment_value");
                //int portfolio_id = resultSet.getInt("portfolio_id"); redundant...
                int quantity = resultSet.getInt("quantity");

                System.out.printf("%-10s %-40s %-15s %-12s %-12.2f %-10d\n",
                        ticker, investment_name, investment_type, risk_level, investment_value, quantity);
            }
        } catch (SQLException e) {
            System.err.println("SQL query execution failed: " + e.getMessage());
        }
    }

    // Method to view portfolios.

    public static void viewPortfolios() {

        String currentUsername = SessionManager.getCurrentUsername();  // Get current username from SessionManager

        String sqlCode = "SELECT * FROM Portfolios_total_value WHERE username = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {
            preparedStatement.setString(1, currentUsername);  // Set username parameter

            ResultSet resultSet = preparedStatement.executeQuery();

            // Print column names as the header
            System.out.printf("\n" + "%-3s %-11s %-20s %-11s %-6s\n",
                    "Id", "Portfolio", "Description", "Username", "Value");

            // Print a line under the header
            System.out.println("--- ----------- -------------------- ----------- ------");

            // Print each row in a supercute formatted manner
            while (resultSet.next()) {

                // Column labels from view
                int portfolio_id = resultSet.getInt("portfolio_id");
                String portfolio_name = resultSet.getString("portfolio_name");
                String description = resultSet.getString("description");
                String username = resultSet.getString("username");
                double total_value = resultSet.getDouble("total_value");

                System.out.printf("%-3d %-11s %-20s %-11s %-6.2f\n",
                        portfolio_id, portfolio_name, description, username, total_value);
            }
        } catch (SQLException e) {
            System.err.println("SQL query execution failed: " + e.getMessage());
        }
    }



    // Method to update securities-------------------------------------------------------------------------------------

    // REMAINING BUGG; YOU CAN UPDATE NON-EXISTENT SECURITIES.

    public static void updateSecurity() {

        // Initialize scanner
        Scanner scanner = new Scanner(System.in);

        // list of available securities
        viewSecurities();


        // REMAINING BUGG; YOU CAN UPDATE NON-EXISTENT SECURITIES.
        // Prompt user for the ticker to update
        System.out.println("Enter the ticker for the security you want to update:");
        String selectedTicker = scanner.nextLine();



        // Store selected ticker in SessionManager
        SessionManager.setCurrentTicker(selectedTicker);

        // Retrieve selected ticker
        String currentTicker = SessionManager.getCurrentTicker();

        // Retrieve current portfolio ID.
        int selectedPortfolioID = DataBaseEngine.getSelectedPortfolioId();

        // Prompt user for new quantity
        System.out.println("Enter the new quantity for the selected security:");
        int newQuantity = scanner.nextInt();  // Assume the user will enter an integer

        // update the 'quantity'
        String sqlCode = """
    UPDATE Product_quantity
    SET quantity = ?
    WHERE portfolio_id = ? AND ticker = ?;
    """;

        Statement statement = null; // Initialize a Statement object to null

        // Use try-with-resources to ensure the PreparedStatement is closed after use
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {

            // Populate
            preparedStatement.setInt(1, newQuantity);          // New quantity to set
            preparedStatement.setInt(2, selectedPortfolioID);  // ID of the portfolio to update
            preparedStatement.setString(3, currentTicker);     // Ticker of the security to update

            // Execute
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Handle exceptions
            System.err.println("SQL query execution failed: " + e.getMessage());
        } finally {
            // Close the Statement object if it was created, to release resources
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

