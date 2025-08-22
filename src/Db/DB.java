package Db;

import Exceptions.DbException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    private static Properties properties = null;
    private static Connection connection = null;


    public DB() {

    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                (new DB()).loadProperties();
                connection = DriverManager.getConnection(properties.getProperty("dburl"), properties.getProperty("user"), properties.getProperty("password"));
                System.out.println("Connetion established: " + connection);
            }
            catch (SQLException e) {
                throw new DbException(e.getMessage());

            }
        }
        else {
            return connection;
        }
        return null;

    }

    public static void disconnectDB() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed");
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

    }


    private void loadProperties() {
        try (FileInputStream fis = new FileInputStream("db.properties")) {
            properties = new Properties();
            properties.load(fis);
            //return properties;
            //properties.load(DB.class.getClassLoader().getResourceAsStream("db.properties"));

        }
        catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }




}
