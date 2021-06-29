package database.services;

import database.connection.DatabaseConnectionManager;
import database.models.Room;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RoomService implements ModelService<Room> {

    private static RoomService roomService = null;

    private RoomService() {
    }

    public static ModelService<Room> getService() {
        if (roomService == null)
            roomService = new RoomService();

        return roomService;
    }

    private void transferRoomIdToModel(PreparedStatement preparedInsertStatement, Room model) throws SQLException {
        try (final var idResult = preparedInsertStatement.getGeneratedKeys()) {
            idResult.next();

            model.setId(idResult.getInt(1));
        }
    }

    private void insertRooms(Collection<Room> models) throws SQLException {

        final var preparedInsertStatement = DatabaseConnectionManager
                .getDatabaseConnection()
                .createPreparedStatementWithReturnGeneratedKeys("INSERT INTO ROOM (NAME) VALUES (?)");

        try (preparedInsertStatement) {
            for (final var model : models) {

                preparedInsertStatement.setString(1, model.getName());

                preparedInsertStatement.executeUpdate();

                transferRoomIdToModel(preparedInsertStatement, model);
            }
        }
    }

    private void updateRooms(Collection<Room> models) throws SQLException {
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

        System.out.println(selectRoomEntriesSqlStringBuilder);

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
    public List<Room> getAllEntries() throws SQLException {
        return getEntries(new ArrayList<>());
    }

    @Override
    public void save(final Collection<Room> models) throws SQLException {

        final var databaseConnection = DatabaseConnectionManager.getDatabaseConnection();

        databaseConnection.setAutoCommit(false);

        insertRooms(models.stream().filter(x -> x.getId() == null).toList());
        updateRooms(models.stream().filter(x -> x.getId() != null).toList());

        databaseConnection.commit();

        databaseConnection.setAutoCommit(true);
    }

    @Override
    public void delete(final Collection<Room> models) throws SQLException {
        final var rooms = models
                .stream()
                .filter(x -> x.getId() != null)
                .toList();

        if (rooms.isEmpty())
            throw new IllegalArgumentException("Only rooms with IDs can be deleted!");

        final var databaseConnection = DatabaseConnectionManager.getDatabaseConnection();

        databaseConnection.setAutoCommit(false);

        final var preparedDeleteStatement = databaseConnection.createPreparedStatement("DELETE FROM ROOM WHERE ID = ?");

        try (preparedDeleteStatement) {
            for (final var room : rooms) {
                preparedDeleteStatement.setInt(1, room.getId());

                preparedDeleteStatement.executeUpdate();
            }

            databaseConnection.commit();
        }

        databaseConnection.setAutoCommit(true);
    }
}
