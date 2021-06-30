package database.services;

import database.connection.DatabaseConnectionManager;
import database.models.Model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseModelService<ModelType extends Model> implements ModelService<ModelType> {

    protected abstract void insertModels(Collection<ModelType> models) throws SQLException;
    protected abstract void updateModels(Collection<ModelType> models) throws SQLException;

    protected void transferIdToModel(PreparedStatement preparedInsertStatement, ModelType model) throws SQLException {
        try (final var idResult = preparedInsertStatement.getGeneratedKeys()) {
            idResult.next();

            model.setId(idResult.getInt(1));
        }
    }

    protected void delete(final Collection<Integer> modelIds, String tableName, String idColumnName) throws SQLException {
        final var existingIds = modelIds
                .stream()
                .filter(x -> x != null)
                .toList();

        if (existingIds.size() != modelIds.size())
            throw new IllegalArgumentException("Some models have missing IDs!");

        final var databaseConnection = DatabaseConnectionManager.getDatabaseConnection();

        databaseConnection.setAutoCommit(false);

        final var preparedDeleteStatement = databaseConnection.createPreparedStatement("DELETE FROM " + tableName + " WHERE " + idColumnName + " = ?");

        try (preparedDeleteStatement) {
            for (final var id : existingIds) {
                preparedDeleteStatement.setInt(1, id);

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

        return preparedGetEntriesStatement;
    }

    protected void clear(String tableName) throws SQLException {
        final var databaseConnection = DatabaseConnectionManager.getDatabaseConnection();

        databaseConnection
                .createPreparedStatement("DELETE FROM " + tableName)
                .executeUpdate();
    }

    @Override
    public List<ModelType> get() throws SQLException {
        return get(new ArrayList<>());
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
