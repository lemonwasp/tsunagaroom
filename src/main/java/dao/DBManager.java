package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Creates JDBC connections using environment-based configuration.
 *
 * <p>No database credentials are stored in source control. Configure
 * CONNECT_ROOM_DB_URL, CONNECT_ROOM_DB_USER, and CONNECT_ROOM_DB_PASSWORD
 * before starting the application.</p>
 */
public final class DBManager {

    private DBManager() {
        // Utility class.
    }

    /**
     * Opens a new database connection using the configured environment variables.
     *
     * @return an open JDBC connection
     * @throws SQLException if configuration is missing or the connection fails
     */
    public static Connection getConnection() throws SQLException {
        String url = requireEnvironmentVariable("CONNECT_ROOM_DB_URL");
        String user = requireEnvironmentVariable("CONNECT_ROOM_DB_USER");
        String password = requireEnvironmentVariable("CONNECT_ROOM_DB_PASSWORD");

        return DriverManager.getConnection(url, user, password);
    }

    private static String requireEnvironmentVariable(String name) throws SQLException {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new SQLException(name + " environment variable is not set.");
        }
        return value;
    }
}
