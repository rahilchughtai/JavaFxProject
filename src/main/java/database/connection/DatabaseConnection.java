package database.connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseConnection {

    void createDefaultDatabaseSchemeIfNotExists() throws SQLException;

    void initializeConnection() throws ClassNotFoundException, IllegalStateException, SQLException;

    PreparedStatement createPreparedStatement(String sql) throws SQLException;

    PreparedStatement createPreparedStatementWithReturnGeneratedKeys(String sql) throws SQLException;

    void setAutoCommit(boolean autoCommit) throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;
}
