package database;

import java.sql.SQLException;

public interface DatabaseConnection {
    void initializeConnection() throws ClassNotFoundException, IllegalStateException, SQLException;
}
