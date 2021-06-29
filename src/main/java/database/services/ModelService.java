package database.services;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface ModelService<ModelType> {
    List<ModelType> getAllEntries() throws SQLException;
    void save(Collection<ModelType> models) throws SQLException;
    void delete(Collection<ModelType> models) throws SQLException;
}
