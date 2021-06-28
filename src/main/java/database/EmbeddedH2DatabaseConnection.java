package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

class EmbeddedH2DatabaseConnection implements DatabaseConnection {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String CONNECTION_STRING_FOR_EMBEDDED_DATABASE = "jdbc:h2:~/group-rahil/student-manager";
    private static final String USERNAME_FOR_EMBEDDED_DATABASE = "sa";
    private static final String PASSWORD_FOR_EMBEDDED_DATABASE = "";

    private Connection databaseConnection = null;

    @Override
    public void createDefaultDatabaseSchemeIfNotExists() throws SQLException {
//        var metaData = databaseConnection.getMetaData();
//
//        ResultSet resultSet = metaData.getTables(null, null, "ROOM", null);
//
//        if (resultSet.next()) {
//            System.out.println("Database scheme was already been created.");
//            return;
//        }

        final var createRoomTableSql = """
                CREATE TABLE IF NOT EXISTS ROOM
                (ID INT,
                NAME VARCHAR(255) NOT NULL,
                PRIMARY KEY (ID))
                """;
        final var createCourseTableSql = """
                CREATE TABLE IF NOT EXISTS COURSE
                (ID INT,
                NAME VARCHAR(255) NOT NULL,
                ROOM_ID INT NOT NULL,
                FOREIGN KEY (ROOM_ID) REFERENCES ROOM(ID),
                PRIMARY KEY (ID))
                """;

        final var createCorporationTableSql = """
                CREATE TABLE IF NOT EXISTS CORPORATION
                (ID INT,
                NAME VARCHAR(255) NOT NULL,
                PRIMARY KEY (ID))
                """;

        final var createStudentTableSql = """
                CREATE TABLE IF NOT EXISTS STUDENT
                (ID INT,
                FIRST_NAME VARCHAR(255) NOT NULL,
                LAST_NAME VARCHAR(255) NOT NULL,
                JAVA_SKILL_RATING INT NOT NULL,
                CORPORATION_ID INT NOT NULL,
                COURSE_ID INT NOT NULL,
                FOREIGN KEY (CORPORATION_ID) REFERENCES CORPORATION(ID),
                FOREIGN KEY (COURSE_ID) REFERENCES COURSE(ID),
                PRIMARY KEY (ID))
                """;

        final var statement = databaseConnection.createStatement();

        statement.executeUpdate(createRoomTableSql);
        statement.executeUpdate(createCourseTableSql);
        statement.executeUpdate(createCorporationTableSql);
        statement.executeUpdate(createStudentTableSql);
    }

    @Override
    public void initializeConnection() throws ClassNotFoundException, SQLException {
        System.out.println("Trying to connect to the embedded H2 database...");

        Class.forName(JDBC_DRIVER);

        databaseConnection = DriverManager.getConnection(CONNECTION_STRING_FOR_EMBEDDED_DATABASE, USERNAME_FOR_EMBEDDED_DATABASE, PASSWORD_FOR_EMBEDDED_DATABASE);

        System.out.println("Embedded H2 database connection was established.");
    }
}
