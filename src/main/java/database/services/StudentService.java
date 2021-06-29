package database.services;

import database.models.Student;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class StudentService extends BaseService<Student> {
    private static StudentService service;

    private StudentService(){
    }

    public static ModelService<Student> getService() {
        if (service == null)
            service = new StudentService();

        return service;
    }

    @Override
    protected void insertModels(final Collection<Student> models) throws SQLException {

    }

    @Override
    protected void updateModels(final Collection<Student> models) throws SQLException {

    }

    @Override
    public List<Student> getEntries(final List<Integer> ids) throws SQLException {
        return null;
    }

    @Override
    public void delete(final Collection<Student> models) throws SQLException {

    }
}
