package database.services;

import database.connection.DatabaseConnectionManager;
import database.models.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StudentService extends BaseService<Student> {
    private static StudentService service;

    private StudentService(){
    }

    public static ModelService<Student> getService() {
        if (service == null)
            service = new StudentService();

        return service;
    }

    @Override
    protected void insertModels(final Collection<Student> models) throws SQLException {

    }

    @Override
    protected void updateModels(final Collection<Student> models) throws SQLException {

    }

    @Override
    public List<Student> getEntries(final List<Integer> ids) throws SQLException {
        final var preparedGetEntriesStatement = getEntriesFromDatabase(
                ids,
                """
                SELECT STUDENT.ID, STUDENT.MATRICULATION_NUMBER, STUDENT.FIRST_NAME, STUDENT.LAST_NAME, STUDENT.JAVA_SKILL_RATING, CORPORATION.ID AS CORPORATION_ID, CORPORATION.NAME AS CORPORATION_NAME
                FROM STUDENT
                JOIN CORPORATION ON STUDENT.CORPORATION_ID = CORPORATION.ID
                """,
                "LAST_NAME",
                "STUDENT"
        );

        final var models = new ArrayList<Student>();

        try (preparedGetEntriesStatement) {

            final var entriesFromDatabase = preparedGetEntriesStatement.executeQuery();

            try (entriesFromDatabase) {
                while (entriesFromDatabase.next()) {
                    final var model = new Student() {{
                        setId(entriesFromDatabase.getInt("ID"));
                        setFirstName(entriesFromDatabase.getString("FIRST_NAME"));
                        setLastName(entriesFromDatabase.getString("LAST_NAME"));
                        setJavaSkillRating(JavaSkillRating.values()[entriesFromDatabase.getInt("JAVA_SKILL_RATING")]);
                        setMatriculationNumber(entriesFromDatabase.getString("MATRICULATION_NUMBER"));
                        setCorporation(new Corporation() {{
                            setId(entriesFromDatabase.getInt("CORPORATION_ID"));
                            setName(entriesFromDatabase.getString("CORPORATION_NAME"));
                        }});
                    }};

                    models.add(model);
                }
            }
        }

        return models;
    }

    @Override
    public void delete(final Collection<Student> models) throws SQLException {
        delete(models, "STUDENT", "ID");
    }
}
