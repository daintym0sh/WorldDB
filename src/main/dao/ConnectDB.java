package main.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used to connect to PostgreSQL database, inherited by all DAO classes
 */
public class ConnectDB {
    private static Connection conn;
    private static String DATABASE = "WorldDB";
    private static String USER = "postgres";
    private static String PASSWORD = "admin";

    /**
     *This method makes a connection to a PostgreSQL database and returns the Connection object
     * @return the Connection object use to connect to a PostgreSQL database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection connect() throws SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/" + DATABASE + "?user=" + USER + "&password=" + PASSWORD;
        conn = DriverManager.getConnection(url, USER, PASSWORD);
        return conn;
    }

}
