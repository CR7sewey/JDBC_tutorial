package Entities.DAO;

import Entities.Interfaces.IDepartmentDao;
import Entities.Interfaces.ISellerDao;

import java.sql.Connection;

public class DaoFactory {

    public static IDepartmentDao getDepartmentDao() {
        return new DepartmentDaoJDBC();
    }

    public static ISellerDao getSellerDao(Connection connection) {
        return new SellerDaoJDBC(connection);
    }
}
