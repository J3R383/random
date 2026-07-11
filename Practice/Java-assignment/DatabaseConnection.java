import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/chuka_university_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "jeremy";
    private static final String DB_PASSWORD = "jarvis";

    // CHANGE THIS!
    public static Connection getConnection() throws SQLException {
        // Optional: Load driver explicitly for older JDBC versions or specific
        // configurations
        // try {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        // } catch (ClassNotFoundException e) {
        // throw new SQLException("MySQL JDBC Driver not found.",e);
        // }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}