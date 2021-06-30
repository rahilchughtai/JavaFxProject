package database.services;

import database.connection.DatabaseConnectionManager;
import database.models.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoomService extends BaseModelService<Room> {

    public static final String TABLE_NAME = "ROOM";

    private static RoomService service = null;

    private RoomService() {
    }

    public static ModelService<Room> getService() {
        if (service == null)
            service = new RoomService();

        return service;
    }

    @Override
    protected void insertModels(Collection<Room> models) throws SQLException {

        final var preparedInsertStatement = DatabaseConnectionManager
                .getDatabaseConnection()
                .createPreparedStatementWithReturnGeneratedKeys("INSERT INTO ROOM (NAME) VALUES (?)");

        try (preparedInsertStatement) {
            for (final var model : models) {

                preparedInsertStatement.setString(1, model.getName());

                preparedInsertStatement.executeUpdate();

                transferIdToModel(preparedInsertStatement, model);
            }
        }
    }

    @Override
    protected void updateModels(Collection<Room> models) throws SQLException {
        final var preparedUpdateStatement = DatabaseConnectionManager
                .getDatabaseConnection()
                .createPreparedStatement("UPDATE ROOM SET NAME = ? WHERE ID = ?");

        try (preparedUpdateStatement) {
            for (final var model : models) {
                preparedUpdateStatement.setString(1, model.getName());
                preparedUpdateStatement.setInt(2, model.getId());

                preparedUpdateStatement.executeUpdate();
            }
        }
    }

    @Override
    public List<Room> get(List<Integer> modelIds) throws SQLException {
        final var preparedGetEntriesStatement = getEntriesFromDatabase(
                modelIds,
                """
                SELECT ID, NAME
                FROM ROOM
                """,
                "NAME",
                TABLE_NAME
        );

        final var models = new ArrayList<Room>();

        try (preparedGetEntriesStatement) {

            final var entriesFromDatabase = preparedGetEntriesStatement.executeQuery();

            try (entriesFromDatabase) {
                while (entriesFromDatabase.next()) {
                    final var model = new Room() {{
                        setId(entriesFromDatabase.getInt("ID"));
                        setName(entriesFromDatabase.getString("NAME"));
                    }};

                    models.add(model);
                }
            }
        }

        return models;
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
