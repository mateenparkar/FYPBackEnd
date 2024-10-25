package org.fyp.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private static Connection conn;
    public static Connection getConnection() throws SQLException {
        String user, password, host, name;

        if(conn != null && !conn.isClosed()){
            return conn;
        }

        try{
            Properties prop = new Properties();
            prop.load(new FileInputStream("src/main/resources/db.properties"));

            user = prop.getProperty("user");
            password = prop.getProperty("password");
            host = prop.getProperty("host");
            name = prop.getProperty("name");

            if(user == null || password == null || host == null){
                throw new Exception("Properties file must exist " +
                        "and must contain user, password, name and host properties");
            }

            conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + name + "?useSSL=false", user, password);
            return conn;

        } catch (Exception e) {
            System.err.println(e.getMessage());

            throw new IllegalArgumentException();
        }
    }
    public static void setConn(Connection connection){
        conn = connection;
    }
}
