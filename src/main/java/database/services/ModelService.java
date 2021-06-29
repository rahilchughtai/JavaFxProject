package database.services;

import database.models.Room;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface ModelService<ModelType> {
    List<Room> getEntries(List<Integer> ids) throws SQLException;

    List<ModelType> getAllEntries() throws SQLException;
    void save(Collection<ModelType> models) throws SQLException;
    void delete(Collection<ModelType> models) throws SQLException;
}
