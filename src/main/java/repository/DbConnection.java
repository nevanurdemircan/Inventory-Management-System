package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private DbConnection() {
        throw new UnsupportedOperationException();
    }

    private static volatile Connection connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/inventory?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123";

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DbConnection.class) {
                if (connection == null) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                        System.out.println("Database Connection Success.");
                    } catch (ClassNotFoundException e) {
                        System.err.println("JDBC Driver Not Found: " + e.getMessage());
                    } catch (SQLException e) {
                        System.err.println("Database Connection Failed: " + e.getMessage());
                        System.err.println("SQL State: " + e.getSQLState());
                        System.err.println("Error Code: " + e.getErrorCode());
                    }
                }
            }
        }
        return connection;
    }
}
