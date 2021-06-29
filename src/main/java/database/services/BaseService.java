package database.services;

import database.connection.DatabaseConnectionManager;
import database.models.Model;
import database.models.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseService<ModelType extends Model> implements ModelService<ModelType> {

    protected abstract void insertModels(Collection<ModelType> models) throws SQLException;
    protected abstract void updateModels(Collection<ModelType> models) throws SQLException;

    protected void transferIdToModel(PreparedStatement preparedInsertStatement, ModelType model) throws SQLException {
        try (final var idResult = preparedInsertStatement.getGeneratedKeys()) {
            idResult.next();

            model.setId(idResult.getInt(1));
        }
    }

    protected void delete(final Collection<ModelType> models, String tableName) throws SQLException {
        final var rooms = models
                .stream()
                .filter(x -> x.getId() != null)
                .toList();

        if (rooms.isEmpty())
            throw new IllegalArgumentException("Only rooms with IDs can be deleted!");

        final var databaseConnection = DatabaseConnectionManager.getDatabaseConnection();

        databaseConnection.setAutoCommit(false);

        final var preparedDeleteStatement = databaseConnection.createPreparedStatement("DELETE FROM " + tableName + " WHERE ID = ?");

        try (preparedDeleteStatement) {
            for (final var room : rooms) {
                preparedDeleteStatement.setInt(1, room.getId());

                preparedDeleteStatement.executeUpdate();
            }

            databaseConnection.commit();
        }

        databaseConnection.setAutoCommit(true);
    }

    protected PreparedStatement getEntriesFromDatabase(List<Integer> ids, String selectAndJoinSql, String orderByColumnName, String tableName) throws SQLException {
        final var databaseConnection = DatabaseConnectionManager.getDatabaseConnection();

        final var selectEntriesSqlStringBuilder = new StringBuilder(selectAndJoinSql);

        if (!ids.isEmpty()) {

            selectEntriesSqlStringBuilder.append("\nWHERE " + tableName + ".ID IN (");

            final var idParameters = ids
                    .stream()
                    .map(x -> "?")
                    .collect(Collectors.joining(","));

            selectEntriesSqlStringBuilder.append(idParameters);

            selectEntriesSqlStringBuilder.append(")");
        }

        selectEntriesSqlStringBuilder.append("\nORDER BY " + orderByColumnName);

        final var preparedGetEntriesStatement = databaseConnection.createPreparedStatement(selectEntriesSqlStringBuilder.toString());

        for (var i = 0; i < ids.size(); i++) {
            preparedGetEntriesStatement.setInt(i+1, ids.get(i));
        }

        System.out.println(selectEntriesSqlStringBuilder);

        return preparedGetEntriesStatement;
    }

    @Override
    public List<ModelType> getAllEntries() throws SQLException {
        return getEntries(new ArrayList<>());
    }

    @Override
    public void save(final Collection<ModelType> models) throws SQLException {
        final var databaseConnection = DatabaseConnectionManager.getDatabaseConnection();

        databaseConnection.setAutoCommit(false);

        insertModels(models.stream().filter(x -> x.getId() == null).toList());
        updateModels(models.stream().filter(x -> x.getId() != null).toList());

        databaseConnection.commit();

        databaseConnection.setAutoCommit(true);
    }
}
