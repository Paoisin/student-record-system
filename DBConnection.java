// Developer: Gel
// Role: Database Engineer
// Description: Handles database connection and disconnection
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Handler
 * Manages JDBC connection to MySQL/PostgreSQL
 * Static method to ensure single connection instance
 */
public class DBConnection {
    
    // Database credentials - UPDATE THESE WITH YOUR DATABASE INFO
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = ""; // Change to your password
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection connection = null;
    
    /**
     * Get database connection
     * Creates connection if not exists
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load MySQL driver
                Class.forName(DB_DRIVER);
                
                // Establish connection
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("✓ Database connected successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("✗ MySQL Driver not found!");
            System.out.println("  Please add mysql-connector-java JAR to your project");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("✗ Connection failed!");
            System.out.println("  Check your database URL, username, password");
            e.printStackTrace();
        }
        
        return connection;
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed");
            }
        } catch (SQLException e) {
            System.out.println("✗ Error closing connection");
            e.printStackTrace();
        }
    }
}
