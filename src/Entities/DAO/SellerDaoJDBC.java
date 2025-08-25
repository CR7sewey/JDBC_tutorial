package Entities.DAO;

import Db.DB;
import Entities.Department;
import Entities.Interfaces.ISellerDao;
import Entities.Seller;
import Exceptions.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellerDaoJDBC implements ISellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) throws SQLException {
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO seller\n" +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId)\n" +
                    "VALUES\n" +
                    "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            //preparedStatement = DB.preparedStatement;
            preparedStatement.setString(1, obj.name);
            preparedStatement.setString(2, obj.email);
            preparedStatement.setDate(3, new java.sql.Date(obj.birthDate.getTime()));
            preparedStatement.setDouble(4, obj.baseSalary);
            preparedStatement.setInt(5, obj.department.getId());


            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                rs = preparedStatement.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    //String name = rs.getString("Name");
                    obj.id = id;
                    System.out.println(id);
                }
                rs.close();
            }
            else{
                throw new DbException("Insert failed");
            }


        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            //assert rs != null;
            //rs.close();
            //DB.disconnectDB();
        }
    }

    @Override
    public void update(Seller obj) throws SQLException {
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE seller\n" +
                    "        SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\n" +
                    "                WHERE Id = ?", Statement.RETURN_GENERATED_KEYS);

            //preparedStatement = DB.preparedStatement;
            preparedStatement.setString(1, obj.name);
            preparedStatement.setString(2, obj.email);
            preparedStatement.setDate(3, new java.sql.Date(obj.birthDate.getTime()));
            preparedStatement.setDouble(4, obj.baseSalary);
            preparedStatement.setInt(5, obj.department.getId());
            preparedStatement.setInt(6, obj.id);


            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                rs = preparedStatement.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    //String name = rs.getString("Name");
                    obj.id = id;
                    System.out.println(id);
                }
                rs.close();
            }
            else{
                throw new DbException("Insert failed");
            }


        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            //assert rs != null;
            //rs.close();
            DB.disconnectDB();
        }


    }

    @Override
    public void deleteById(Integer id) throws SQLException {


    }

    @Override
    public Seller findById(Integer id) throws SQLException {
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        Seller seller = new Seller();
        try {
            if (connection == null) {
                connection = DB.getConnection();
            }
            preparedStatement = connection.prepareStatement("""
                    SELECT seller.*,department.Name as DepName
                    FROM seller INNER JOIN department
                    ON seller.DepartmentId = department.Id
                    WHERE seller.Id = ?""");
            //preparedStatement = DB.preparedStatement;
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) { // pq cursor na zero!

                Department dep = instatiateDepartment(rs);
                seller = instatiateSeller(rs, dep);

            }

            return seller;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            //assert rs != null;
            //rs.close();
            DB.disconnectDB();
        }
    }

    @Override
    public List<Seller> findAll() throws SQLException {
        List<Seller> sellers = new ArrayList<>();
        ResultSet resultSet = DB.getResultSet("SELECT seller.*,department.Name as DepName\n" +
                "FROM seller INNER JOIN department\n" +
                "ON seller.DepartmentId = department.Id\n" +
                "ORDER BY Name");
        while (resultSet.next()) {
            var department = instatiateDepartment(resultSet);
            var seller = instatiateSeller(resultSet, department);
            sellers.add(seller);
        }
        return sellers;
    }

    private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller seller = new Seller();
        seller.id = rs.getInt("id"); // should be the method but im lazy
        seller.name = rs.getString("name");
        seller.email = rs.getString("email");
        seller.baseSalary = rs.getDouble("baseSalary");
        seller.birthDate = rs.getDate("birthDate");
        seller.department = dep;
        return seller;
    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("id"));
        dep.setName(rs.getString("name"));
        return dep;
    }
}
