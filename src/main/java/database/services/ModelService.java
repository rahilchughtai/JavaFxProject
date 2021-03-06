package database.services;

import database.models.Model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface ModelService<ModelType extends Model> {
    List<ModelType> get() throws SQLException;
    List<ModelType> get(List<Integer> modelIds) throws SQLException;
    void save(ModelType model) throws SQLException;
    void save(Collection<ModelType> models) throws SQLException;
    boolean isEmpty() throws SQLException;
    void delete(Integer modelId) throws SQLException;
    void delete(Collection<Integer> modelIds) throws SQLException;
    void clear() throws SQLException;
}
