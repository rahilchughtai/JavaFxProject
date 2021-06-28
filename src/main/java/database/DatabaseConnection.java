package database;

import java.sql.SQLException;

public interface DatabaseConnection {
    void createDefaultDatabaseSchemeIfNotExists() throws SQLException;

    void initializeConnection() throws ClassNotFoundException, IllegalStateException, SQLException;
}
