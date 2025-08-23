package Entities.Interfaces;

import Entities.Department;

import java.util.List;

public interface IDepartmentDao {

    public void insert(Department obj);
    public void update(Department obj);
    public void deleteById(Integer id);
    public Department findById(Integer id);
    public List<Department> findAll();

}
