package Entities.Interfaces;

import Entities.Seller;

import java.sql.SQLException;
import java.util.List;

public interface ISellerDao {


        public void insert(Seller obj) throws SQLException;
        public void update(Seller obj) throws SQLException;
        public void deleteById(Integer id) throws SQLException;
        public Seller findById(Integer id) throws SQLException;
        public List<Seller> findAll() throws SQLException;

}
