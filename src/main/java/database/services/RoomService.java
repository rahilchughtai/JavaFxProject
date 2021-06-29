package database.services;

import database.connection.DatabaseConnectionManager;
import database.models.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RoomService extends BaseService<Room> {

    private static RoomService roomService = null;

    private RoomService() {
    }

    public static ModelService<Room> getService() {
        if (roomService == null)
            roomService = new RoomService();

        return roomService;
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
    public List<Room> getEntries(List<Integer> ids) throws SQLException {
        final var preparedGetEntriesStatement = getEntriesFromDatabase(
                ids,
                """
                SELECT ID, NAME
                FROM ROOM
                """,
                "NAME"
        );

        final var models = new ArrayList<Room>();

        try (preparedGetEntriesStatement) {

            for (var i = 0; i < ids.size(); i++) {
                preparedGetEntriesStatement.setInt(i+1, ids.get(i));
            }

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
    public void delete(final Collection<Room> models) throws SQLException {
        delete(models, "ROOM");
    }
}
