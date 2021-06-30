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
        final var preparedInsertStatement = DatabaseConnectionManager
                .getDatabaseConnection()
                .createPreparedStatementWithReturnGeneratedKeys(
                        """
                            INSERT INTO STUDENT (
                            	MATRICULATION_NUMBER,
                            	FIRST_NAME,
                            	LAST_NAME,
                            	JAVA_SKILL_RATING,
                            	CORPORATION_ID,
                            	COURSE_ID
                            	)
                            	VALUES (
                            	    ?,
                            	    ?,
                            	    ?,
                            	    ?,
                            	    ?,
                            	    ?
                            	)
                            """
                );

        try (preparedInsertStatement) {
            for (final var model : models) {

                preparedInsertStatement.setString(1, model.getMatriculationNumber());
                preparedInsertStatement.setString(2, model.getFirstName());
                preparedInsertStatement.setString(3, model.getLastName());
                preparedInsertStatement.setInt(4, model.getJavaSkillRating().getValue());
                preparedInsertStatement.setInt(5, model.getCorporation().getId());
                preparedInsertStatement.setInt(6, model.getCourse().getId());

                preparedInsertStatement.executeUpdate();

                transferIdToModel(preparedInsertStatement, model);
            }
        }
    }

    @Override
    protected void updateModels(final Collection<Student> models) throws SQLException {
        final var preparedUpdateStatement = DatabaseConnectionManager
                .getDatabaseConnection()
                .createPreparedStatement(
                        """
                            UPDATE STUDENT SET
                            	MATRICULATION_NUMBER = ?,
                            	FIRST_NAME = ?,
                            	LAST_NAME = ?,
                            	JAVA_SKILL_RATING = ?,
                            	CORPORATION_ID = ?,
                            	COURSE_ID = ?
                            WHERE
                            	ID = ?
                            """
                );

        try (preparedUpdateStatement) {
            for (final var model : models) {
                preparedUpdateStatement.setString(1, model.getMatriculationNumber());
                preparedUpdateStatement.setString(2, model.getFirstName());
                preparedUpdateStatement.setString(3, model.getLastName());
                preparedUpdateStatement.setInt(4, model.getJavaSkillRating().getValue());
                preparedUpdateStatement.setInt(5, model.getCorporation().getId());
                preparedUpdateStatement.setInt(6, model.getCourse().getId());
                preparedUpdateStatement.setInt(7, model.getId());

                preparedUpdateStatement.executeUpdate();
            }
        }
    }

    @Override
    public List<Student> getEntries(final List<Integer> modelIds) throws SQLException {
        final var preparedGetEntriesStatement = getEntriesFromDatabase(
                modelIds,
                """
                 SELECT
                 	STUDENT.ID,
                 	STUDENT.MATRICULATION_NUMBER,
                 	STUDENT.FIRST_NAME,
                 	STUDENT.LAST_NAME,
                 	STUDENT.JAVA_SKILL_RATING,
                 	CORPORATION.ID AS CORPORATION_ID,
                 	CORPORATION.NAME AS CORPORATION_NAME,
                 	COURSE.ID AS COURSE_ID,
                 	COURSE.NAME AS COURSE_NAME,
                 	ROOM.ID  AS COURSE_ROOM_ID,
                 	ROOM.NAME AS COURSE_ROOM_NAME
                 FROM STUDENT
                 JOIN CORPORATION ON STUDENT.CORPORATION_ID = CORPORATION.ID
                 JOIN COURSE ON STUDENT.COURSE_ID = COURSE.ID
                 JOIN ROOM ON COURSE.ROOM_ID = ROOM.ID
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
                        setJavaSkillRating(JavaSkillRating.valueOf(entriesFromDatabase.getInt("JAVA_SKILL_RATING")));
                        setMatriculationNumber(entriesFromDatabase.getString("MATRICULATION_NUMBER"));
                        setCorporation(new Corporation() {{
                            setId(entriesFromDatabase.getInt("CORPORATION_ID"));
                            setName(entriesFromDatabase.getString("CORPORATION_NAME"));
                        }});
                        setCourse(new Course() {{
                            setId(entriesFromDatabase.getInt("COURSE_ID"));
                            setName(entriesFromDatabase.getString("COURSE_NAME"));
                            setRoom(new Room() {{
                                setId(entriesFromDatabase.getInt("COURSE_ROOM_ID"));
                                setName(entriesFromDatabase.getString("COURSE_ROOM_NAME"));
                            }});
                        }});
                    }};

                    models.add(model);
                }
            }
        }

        return models;
    }

    @Override
    public void delete(final Collection<Integer> modelIds) throws SQLException {
        delete(modelIds, "STUDENT", "ID");
    }
}
