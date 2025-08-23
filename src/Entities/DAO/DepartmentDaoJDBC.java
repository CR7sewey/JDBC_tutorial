package Entities.DAO;

import Entities.Department;
import Entities.Interfaces.IDepartmentDao;

import java.util.List;

public class DepartmentDaoJDBC implements IDepartmentDao {
    @Override
    public void insert(Department obj) {

    }

    @Override
    public void update(Department obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return List.of();
    }
}
