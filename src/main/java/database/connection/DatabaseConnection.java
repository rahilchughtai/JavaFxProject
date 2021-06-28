package database.connection;

import java.sql.SQLException;

public interface DatabaseConnection {

    void createDefaultDatabaseSchemeIfNotExists() throws SQLException;

    void initializeDatabaseWithTestData();

    void initializeConnection() throws ClassNotFoundException, IllegalStateException, SQLException;
}
