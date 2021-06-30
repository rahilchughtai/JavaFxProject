package database.services;

import database.connection.DatabaseConnectionManager;
import database.models.Course;
import database.models.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CourseService extends BaseModelService<Course> {

    public static final String TABLE_NAME = "COURSE";

    private static CourseService service = null;

    private CourseService() {
    }

    public static ModelService<Course> getService() {
        if (service == null)
            service = new CourseService();

        return service;
    }

    @Override
    protected void insertModels(final Collection<Course> models) throws SQLException {
        final var preparedInsertStatement = DatabaseConnectionManager
                .getDatabaseConnection()
                .createPreparedStatementWithReturnGeneratedKeys("INSERT INTO COURSE (NAME, ROOM_ID) VALUES (?, ?)");

        try (preparedInsertStatement) {
            for (final var model : models) {

                preparedInsertStatement.setString(1, model.getName());
                preparedInsertStatement.setInt(2, model.getRoom().getId());

                preparedInsertStatement.executeUpdate();

                transferIdToModel(preparedInsertStatement, model);
            }
        }
    }

    @Override
    protected void updateModels(final Collection<Course> models) throws SQLException {
        final var preparedUpdateStatement = DatabaseConnectionManager
                .getDatabaseConnection()
                .createPreparedStatement("UPDATE COURSE SET NAME = ?, ROOM_ID = ? WHERE ID = ?");

        try (preparedUpdateStatement) {
            for (final var model : models) {
                preparedUpdateStatement.setString(1, model.getName());
                preparedUpdateStatement.setInt(2, model.getRoom().getId());
                preparedUpdateStatement.setInt(3, model.getId());

                preparedUpdateStatement.executeUpdate();
            }
        }
    }

    @Override
    public List<Course> get(final List<Integer> modelIds) throws SQLException {

        final var preparedGetEntriesStatement = getEntriesFromDatabase(
                modelIds,
                """
                SELECT COURSE.ID, COURSE.NAME, ROOM.ID AS ROOM_ID, ROOM.NAME AS ROOM_NAME
                FROM COURSE
                JOIN ROOM ON COURSE.ROOM_ID = ROOM.ID
                """,
                "NAME",
                TABLE_NAME
        );

        final var models = new ArrayList<Course>();

        try (preparedGetEntriesStatement) {

            final var entriesFromDatabase = preparedGetEntriesStatement.executeQuery();

            try (entriesFromDatabase) {
                while (entriesFromDatabase.next()) {
                    final var model = new Course() {{
                        setId(entriesFromDatabase.getInt("ID"));
                        setName(entriesFromDatabase.getString("NAME"));
                        setRoom(new Room() {{
                            setId(entriesFromDatabase.getInt("ROOM_ID"));
                            setName(entriesFromDatabase.getString("ROOM_NAME"));
                        }});
                    }};

                    models.add(model);
                }
            }
        }

        return models;
    }

    @Override
    public boolean isEmpty() throws SQLException {
        return isEmpty(TABLE_NAME);
    }

    @Override
    public void delete(final Collection<Integer> modelIds) throws SQLException {
        delete(modelIds, TABLE_NAME, "ID");
    }

    @Override
    public void clear() throws SQLException {
        clear(TABLE_NAME);
    }
}
