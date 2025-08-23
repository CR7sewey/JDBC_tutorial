package Entities.DAO;

import Db.DB;
import Entities.Department;
import Entities.Interfaces.ISellerDao;
import Entities.Seller;
import Exceptions.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements ISellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) throws SQLException {
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        Seller seller = new Seller();
        try {
            DB.getPreparedStatement("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "WHERE seller.Id = ?");
            preparedStatement = DB.preparedStatement;
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) { // pq cursor na zero!

                Department dep = new Department();
                dep.setId(rs.getInt("DepartmentId"));
                dep.setName(rs.getString("DepName"));

                seller.id = rs.getInt("id"); // should be the method but im lazy
                seller.name = rs.getString("name");
                seller.email = rs.getString("email");
                seller.baseSalary = rs.getDouble("baseSalary");
                seller.birthDate = rs.getDate("birthDate");
                seller.department = dep;

            }

            return seller;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.disconnectDB();
        }
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
