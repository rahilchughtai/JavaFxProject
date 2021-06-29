package database.services;

import database.models.Model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface ModelService<ModelType extends Model> {
    List<ModelType> getEntries(List<Integer> ids) throws SQLException;
    List<ModelType> getAllEntries() throws SQLException;
    void save(Collection<ModelType> models) throws SQLException;
    void delete(Collection<ModelType> models) throws SQLException;
}
