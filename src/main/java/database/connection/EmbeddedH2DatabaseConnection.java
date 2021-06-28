package database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class EmbeddedH2DatabaseConnection implements DatabaseConnection {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String CONNECTION_STRING_FOR_EMBEDDED_DATABASE = "jdbc:h2:~/group-rahil/student-manager";
    private static final String USERNAME_FOR_EMBEDDED_DATABASE = "sa";
    private static final String PASSWORD_FOR_EMBEDDED_DATABASE = "";

    private Connection databaseConnection = null;

    @Override
    public void createDefaultDatabaseSchemeIfNotExists() throws SQLException {

        final var createRoomTableSql = """
                CREATE TABLE IF NOT EXISTS ROOM
                (ID INT AUTO_INCREMENT,
                NAME VARCHAR(255) NOT NULL,
                PRIMARY KEY (ID))
                """;
        final var createCourseTableSql = """
                CREATE TABLE IF NOT EXISTS COURSE
                (ID INT AUTO_INCREMENT,
                NAME VARCHAR(255) NOT NULL,
                ROOM_ID INT NOT NULL,
                FOREIGN KEY (ROOM_ID) REFERENCES ROOM(ID),
                PRIMARY KEY (ID))
                """;

        final var createCorporationTableSql = """
                CREATE TABLE IF NOT EXISTS CORPORATION
                (ID INT AUTO_INCREMENT,
                NAME VARCHAR(255) NOT NULL,
                PRIMARY KEY (ID))
                """;

        final var createStudentTableSql = """
                CREATE TABLE IF NOT EXISTS STUDENT
                (ID INT AUTO_INCREMENT,
                MATRICULATION_NUMBER VARCHAR(255) UNIQUE,
                FIRST_NAME VARCHAR(255) NOT NULL,
                LAST_NAME VARCHAR(255) NOT NULL,
                JAVA_SKILL_RATING INT,
                CORPORATION_ID INT NOT NULL,
                FOREIGN KEY (CORPORATION_ID) REFERENCES CORPORATION(ID),
                PRIMARY KEY (ID))
                """;

        final var createStudentCourseTableSql = """
                CREATE TABLE IF NOT EXISTS STUDENT_COURSE
                (STUDENT_ID INT NOT NULL,
                COURSE_ID INT NOT NULL,
                FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT(ID),
                FOREIGN KEY (COURSE_ID) REFERENCES COURSE(ID))
                """;

        final var statement = databaseConnection.createStatement();

        statement.executeUpdate(createRoomTableSql);
        statement.executeUpdate(createCourseTableSql);
        statement.executeUpdate(createCorporationTableSql);
        statement.executeUpdate(createStudentTableSql);
        statement.executeUpdate(createStudentCourseTableSql);
    }

    @Override
    public void initializeDatabaseWithTestData() {

    }

    @Override
    public void initializeConnection() throws ClassNotFoundException, SQLException {
        System.out.println("Trying to connect to the embedded H2 database...");

        Class.forName(JDBC_DRIVER);

        databaseConnection = DriverManager.getConnection(CONNECTION_STRING_FOR_EMBEDDED_DATABASE, USERNAME_FOR_EMBEDDED_DATABASE, PASSWORD_FOR_EMBEDDED_DATABASE);

        System.out.println("Embedded H2 database connection was established.");
    }
}
