import Db.DB;
import Entities.DAO.SellerDaoJDBC;
import Entities.Interfaces.ISellerDao;

import java.sql.SQLException;

public class MainProject {
    public static void main(String[] args) throws SQLException {

        DB db = new DB();
        var con = DB.getConnection();

        ISellerDao seller = new SellerDaoJDBC(con);
        var val = seller.findById(1);
        System.out.println(val);

        System.out.println(seller);
        var val2 = seller.findAll();
        System.out.println(val2);

    }
}
