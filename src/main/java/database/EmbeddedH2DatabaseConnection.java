package database;

import java.sql.DriverManager;
import java.sql.SQLException;

class EmbeddedH2DatabaseConnection implements DatabaseConnection {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String CONNECTION_STRING_FOR_EMBEDDED_DATABASE = "jdbc:h2:~/student-manager";
    private static final String USERNAME_FOR_EMBEDDED_DATABASE = "sa";
    private static final String PASSWORD_FOR_EMBEDDED_DATABASE = "";

    @Override
    public void initializeConnection() throws ClassNotFoundException, SQLException {
        System.out.println("Trying to connect to the embedded H2 database...");

        Class.forName(JDBC_DRIVER);
        DriverManager.getConnection(CONNECTION_STRING_FOR_EMBEDDED_DATABASE, USERNAME_FOR_EMBEDDED_DATABASE, PASSWORD_FOR_EMBEDDED_DATABASE);

        System.out.println("Embedded H2 database connection was established.");
    }
}
