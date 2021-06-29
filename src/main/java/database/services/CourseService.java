package database.services;

import database.connection.DatabaseConnectionManager;
import database.models.Course;
import database.models.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// TODO Student Verknüpfung einbauen!
public class CourseService extends BaseService<Course> {
    @Override
    protected void insertModels(final Collection<Course> models) throws SQLException {

    }

    @Override
    protected void updateModels(final Collection<Course> models) throws SQLException {

    }

    @Override
    public List<Course> getEntries(final List<Integer> ids) throws SQLException {
//        final var databaseConnection = DatabaseConnectionManager.getDatabaseConnection();
//
//        final var courses = new ArrayList<Course>();
//
//        final var selectCourseEntriesSqlStringBuilder = new StringBuilder(
//                """
//                        SELECT COURSE.ID, COURSE.NAME, ROOM.NAME AS ROOM_NAME
//                        FROM COURSE
//                        JOIN ROOM ON COURSE.ROOM_ID = ROOM.ID
//                        """);
//
//        if (!ids.isEmpty()) {
//
//            selectCourseEntriesSqlStringBuilder.append("\nWHERE ID IN (");
//
//            final var idParameters = ids
//                    .stream()
//                    .map(x -> "?")
//                    .collect(Collectors.joining(","));
//
//            selectCourseEntriesSqlStringBuilder.append(idParameters);
//
//            selectCourseEntriesSqlStringBuilder.append(")");
//
//        }
//
//        selectCourseEntriesSqlStringBuilder.append("\nORDER BY NAME");
//
//        try (final var roomsPreparedStatement = databaseConnection.createPreparedStatement(selectCourseEntriesSqlStringBuilder.toString())) {
//
//            for (var i = 0; i < ids.size(); i++) {
//                roomsPreparedStatement.setInt(i+1, ids.get(i));
//            }
//
//            final var roomsFromDatabase = roomsPreparedStatement.executeQuery();
//
//            try (roomsFromDatabase) {
//                while (roomsFromDatabase.next()) {
//                    final var room = new Room() {{
//                        setId(roomsFromDatabase.getInt("ID"));
//                        setName(roomsFromDatabase.getString("NAME"));
//                    }};
//
//                    courses.add(room);
//                }
//            }
        return null;
    }

    @Override
    public void delete(final Collection<Course> models) throws SQLException {
        delete(models, "COURSE");
    }
}
