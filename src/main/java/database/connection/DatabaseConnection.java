package database.connection;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseConnection {

    void createDefaultDatabaseSchemeIfNotExists() throws SQLException;

    void initializeConnection() throws ClassNotFoundException, IllegalStateException, SQLException;

    void executeUpdate(String sql) throws SQLException;

    ResultSet executeQuery(String sql) throws SQLException;
}
