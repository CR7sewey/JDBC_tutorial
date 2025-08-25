import Db.DB;
import Entities.DAO.SellerDaoJDBC;
import Entities.Department;
import Entities.Interfaces.ISellerDao;
import Entities.Seller;

import java.sql.SQLException;
import java.util.Date;

public class MainProject {
    public static void main(String[] args) throws SQLException {

        DB db = new DB();
        var con = DB.getConnection();

        // SELLER

        // Get by ID
        ISellerDao seller = new SellerDaoJDBC(con);
       /* var val = seller.findById(1);
        System.out.println(val);

        System.out.println(seller);
        // Get ALL
        var val2 = seller.findAll();
        System.out.println(val2);*/

        // Insert one
        seller.insert(
                new Seller(
                        25,
                        "TESTE",
        "teste@gmail",
        new Date(),
        2000.00,
                        new Department(
                                2, null
                        )
                )
        );
        con = DB.getConnection();
        seller = new SellerDaoJDBC(con);

       // var val10 = seller.findById(10);
        //System.out.println(val10);

        // Delete One
       // seller.deleteById();

        seller.update(new Seller(
                        10,
                        "TESTE 123",
                        "teste@gmail",
                        new Date(),
                        10000.00,
                        new Department(
                                2, null
                        )
                )
        );
        seller = new SellerDaoJDBC(DB.getConnection());
        Seller val10 = seller.findById(10);
        System.out.println(val10);
    }
}
