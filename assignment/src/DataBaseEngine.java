import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class DataBaseEngine {
    static Connection connection = null;


    // CONNECT ---------------------------------------------------------------------------------------------------------
    public static void establishConnection(){

        try{
            Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Driver setup complete");

            String url = "jdbc:mariadb://atlantis.informatik.umu.se/frih2310_db_ht2023";
            Admin myadmin = new Admin();
            String user = myadmin.getAdmin();
            Password mypassword = new Password();
            String password = mypassword.getPassword();

            try {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connection successful");
            } catch (SQLException ex) {
                System.err.println("Error" + ex.toString());
            }

        } catch (ClassNotFoundException e){
            System.err.println("Error");
            e.printStackTrace();
        }
    }

    // DISCONNECT ------------------------------------------------------------------------------------------------------
    public static void abortConnection(){
        try {
            connection.close();
            System.out.println("Disconnected");
        } catch (SQLException ex) {
            System.err.println("Error " + ex.toString());
        }
    }


    public static void printUser(User createdUser){
        System.out.println(createdUser.getUsername());
    }



    public static void addUser(User user) {

        String sqlCode = """
        INSERT INTO Users (username, name, surname, email, password, date_of_birth)
        SELECT ?, ?, ?, ?, ?, ?
        WHERE NOT EXISTS (
            SELECT 1 FROM Users WHERE username = ?
        );
        """;
        Statement statement = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCode)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, "Jesper");
            preparedStatement.setString(3, "Soder");
            preparedStatement.setString(4, "jesperaterkex@hotmail.com");
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, "1965-12-24");
            preparedStatement.setString(7, user.getUsername());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




}
