package database.services;

import database.connection.DatabaseConnectionManager;
import database.models.Room;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomService implements ModelService<Room> {

    private static RoomService roomService = null;

    private RoomService() {
    }

    public static ModelService<Room> getRoomService() {
        if (roomService == null)
            roomService = new RoomService();

        return roomService;
    }

    private String generateInsertRoomsSql(Collection<Room> models) {
        if (models.isEmpty())
            return "";

        final var roomsInsertSqlStringBuilder = new StringBuilder("INSERT INTO ROOM (NAME) VALUES ");

        roomsInsertSqlStringBuilder.append(models
                        .stream()
                        .map(model -> "('" + model.getName() + "')")
                        .collect(Collectors.joining(",")));

        return roomsInsertSqlStringBuilder.toString();
    }

    private String generateUpdateRoomsSql(Collection<Room> models) {
        return models
                .stream()
                .map(model -> "UPDATE ROOM SET NAME = '" + model.getName() + "' WHERE ID = " + model.getId())
                .collect(Collectors.joining(";"));
    }

    @Override
    public List<Room> getAllEntries() throws SQLException {
        final var databaseConnection = DatabaseConnectionManager.getDatabaseConnection();

        final var roomsDatabaseResult = databaseConnection.executeQuery("""
                SELECT ID, NAME
                FROM ROOM
                ORDER BY NAME
                """);

        final var rooms = new LinkedList<Room>();

        while (roomsDatabaseResult.next()) {
            final var room = new Room() {{
                setId(roomsDatabaseResult.getInt("ID"));
                setName(roomsDatabaseResult.getString("NAME"));
            }};

            rooms.add(room);
        }

        return rooms;
    }

    @Override
    public void save(final Collection<Room> models) throws SQLException {

        final var sqlForNewRooms = generateInsertRoomsSql(models.stream().filter(x -> x.getId() == null).toList());
        final var sqlForExistingRooms = generateUpdateRoomsSql(models.stream().filter(x -> x.getId() != null).toList());

        final var databaseConnection = DatabaseConnectionManager.getDatabaseConnection();

        databaseConnection.executeUpdate(sqlForNewRooms);
        databaseConnection.executeUpdate(sqlForExistingRooms);
    }

    @Override
    public void delete(final Collection<Room> models) throws SQLException {
        final var roomIds = models
                .stream()
                .filter(x -> x.getId() != null)
                .map(x -> x.getId().toString())
                .toList();

        if (roomIds.isEmpty())
            throw new IllegalArgumentException("Only rooms with IDs can be deleted!");

        final var roomsDeleteSql = "DELETE FROM ROOM WHERE ID IN (" + String.join(",", roomIds) + ")";

        DatabaseConnectionManager.getDatabaseConnection().executeUpdate(roomsDeleteSql);
    }
}
