package database.connection;

import java.sql.SQLException;

public class DatabaseConnectionManager {

    private static DatabaseConnection databaseConnection = null;

    public static DatabaseConnection getDatabaseConnection() {
        if (databaseConnection == null)
            throw new IllegalStateException("Database connection wasn't initialized!");

        return databaseConnection;
    }

    public static void initializeDatabase() throws ClassNotFoundException, IllegalStateException, SQLException {
        if (databaseConnection != null)
            throw new IllegalStateException("Database connection was already been established!");

        databaseConnection = new EmbeddedH2DatabaseConnection();
        databaseConnection.initializeConnection();
        databaseConnection.createDefaultDatabaseSchemeIfNotExists();
    }

}
