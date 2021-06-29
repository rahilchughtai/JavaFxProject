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
        final var databaseConnection = DatabaseConnectionManager.getDatabaseConnection();

        final var rooms = new ArrayList<Room>();

        final var selectRoomEntriesSqlStringBuilder = new StringBuilder(
                """
                SELECT ID, NAME
                FROM ROOM
                """);

        if (!ids.isEmpty()) {

            selectRoomEntriesSqlStringBuilder.append("\nWHERE ID IN (");

            final var idParameters = ids
                    .stream()
                    .map(x -> "?")
                    .collect(Collectors.joining(","));

            selectRoomEntriesSqlStringBuilder.append(idParameters);

            selectRoomEntriesSqlStringBuilder.append(")");

        }

        selectRoomEntriesSqlStringBuilder.append("\nORDER BY NAME");

        try (final var roomsPreparedStatement = databaseConnection.createPreparedStatement(selectRoomEntriesSqlStringBuilder.toString())) {

            for (var i = 0; i < ids.size(); i++) {
                roomsPreparedStatement.setInt(i+1, ids.get(i));
            }

            final var roomsFromDatabase = roomsPreparedStatement.executeQuery();

            try (roomsFromDatabase) {
                while (roomsFromDatabase.next()) {
                    final var room = new Room() {{
                        setId(roomsFromDatabase.getInt("ID"));
                        setName(roomsFromDatabase.getString("NAME"));
                    }};

                    rooms.add(room);
                }
            }
        }

        return rooms;
    }

    @Override
    public void delete(final Collection<Room> models) throws SQLException {
        delete(models, "ROOM");
    }
}
