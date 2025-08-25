package Db;

import Exceptions.DbException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Properties properties = null;
    private static Connection connection = null;
    private static Statement statement = null;
    public static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;


    public DB() {

    }

    // DB connection + disconnection
    public static Connection getConnection() {
        if (connection == null) {
            try {
                (new DB()).loadProperties();
                connection = DriverManager.getConnection(properties.getProperty("dburl"), properties.getProperty("user"), properties.getProperty("password"));
                System.out.println("Connetion established: " + connection);
                connection.setAutoCommit(false);
            }
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        else {
            return connection;
        }
        return connection;
    }

    public static void disconnectDB() throws SQLException {
        if (connection != null) {
            try {
                connection.commit();

                //connection.close();
                System.out.println("Connection closed");
            } catch (SQLException e) {
                try {
                    connection.rollback();
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
                throw new DbException(e.getMessage());
            }
            finally {
                DB.closeAll();
            }
        }

    }

    // object with the result of query - table
    public static ResultSet getResultSet(String query) {
        try {
            if (connection == null) {
                DB.getConnection();
            }
            if (statement == null) {
                DB.getStatementGET();
            }

            resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    // Introduce parameters
    public static void getPreparedStatement(String query) throws SQLException {
        try {
            if (connection == null) {
                getConnection();
            }
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        }
        catch (SQLException e) {
            closeAll();
            throw new DbException(e.getMessage());
        }
    }

    // Utils
    // Statement to create sql query
    private static void getStatementGET() {
        try {
            System.out.println(connection);
            if (connection != null) {
                connection = null;
                getConnection();
            }
            statement = connection.createStatement();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());

        }
     }



     public static void closeAll() throws SQLException {
         if (resultSet != null) {
             resultSet.close();
         }
         if (statement != null) {
             statement.close();
         }
         if (preparedStatement != null) {
             preparedStatement.close();
         }
         if (connection != null) {
             connection.close();
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
