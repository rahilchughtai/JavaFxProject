package database.services;

import database.connection.DatabaseConnectionManager;
import database.models.Model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
