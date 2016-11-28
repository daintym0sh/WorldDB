package main.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    Connection conn;
    private final String DATABASE = "WorldDB";
    private final String USER = "postgres";
    private final String PASSWORD = "admin";

    public void connect() throws SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/" + DATABASE + "?user=" + USER + "&password=" + PASSWORD;
        conn = DriverManager.getConnection(url, USER, PASSWORD);
    }

}
