import Db.DB;
import Exceptions.DbException;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
       // DB db = new DB();
        Connection connection = DB.getConnection();
        ResultSet resultSet = DB.getResultSet("select * from department");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("Id") + ": " + resultSet.getString("name"));
        }


        // Insert data
        try {
            PreparedStatement preparedStatement = null;
            DB.getPreparedStatement("insert into department(id, name) values (?, ?)");
            preparedStatement = DB.preparedStatement;
            preparedStatement.setInt(1, 7);
            preparedStatement.setString(2, "Juanito");
            int rows_affected = preparedStatement.executeUpdate();
            System.out.println( rows_affected + " rows affected");

            if (rows_affected > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    //String name = rs.getString("Name");
                    System.out.println(id);
                }
            }
            else{
                System.out.println( "Insert failed");
            }

            throw new DbException("De proposito");


        }
        catch (SQLException e) {
            System.out.println(e.getMessage());

        }




        DB.disconnectDB();
    }
}