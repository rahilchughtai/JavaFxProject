package database.services;

import database.connection.DatabaseConnectionManager;
import database.models.Corporation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CorporationService extends BaseModelService<Corporation> {

    public static final String TABLE_NAME = "CORPORATION";
    private static CorporationService service;

    private CorporationService(){
    }

    public static ModelService<Corporation> getService() {
        if (service == null)
            service = new CorporationService();

        return service;
    }

    @Override
    protected void insertModels(final Collection<Corporation> models) throws SQLException {
        final var preparedInsertStatement = DatabaseConnectionManager
                .getDatabaseConnection()
                .createPreparedStatementWithReturnGeneratedKeys("INSERT INTO CORPORATION (NAME) VALUES (?)");

        try (preparedInsertStatement) {
            for (final var model : models) {

                preparedInsertStatement.setString(1, model.getName());

                preparedInsertStatement.executeUpdate();

                transferIdToModel(preparedInsertStatement, model);
            }
        }
    }

    @Override
    protected void updateModels(final Collection<Corporation> models) throws SQLException {
        final var preparedUpdateStatement = DatabaseConnectionManager
                .getDatabaseConnection()
                .createPreparedStatement("UPDATE CORPORATION SET NAME = ? WHERE ID = ?");

        try (preparedUpdateStatement) {
            for (final var model : models) {
                preparedUpdateStatement.setString(1, model.getName());
                preparedUpdateStatement.setInt(2, model.getId());

                preparedUpdateStatement.executeUpdate();
            }
        }
    }

    @Override
    public List<Corporation> get(final List<Integer> modelIds) throws SQLException {
        final var preparedGetEntriesStatement = getEntriesFromDatabase(
                modelIds,
                """
                SELECT ID, NAME
                FROM CORPORATION
                """,
                "NAME",
                TABLE_NAME
        );

        final var models = new ArrayList<Corporation>();

        try (preparedGetEntriesStatement) {

            final var entriesFromDatabase = preparedGetEntriesStatement.executeQuery();

            try (entriesFromDatabase) {
                while (entriesFromDatabase.next()) {
                    final var model = new Corporation() {{
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
