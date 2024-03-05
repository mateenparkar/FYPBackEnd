package org.fyp.db;

import org.fyp.cli.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
    private DatabaseConnector databaseConnector;

    public UserDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public User getUserById(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM Users "
                + "WHERE user_id = " + id);

        if (rs.next()) {
            User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password")
            );

            return user;
        }

        return null;
    }
}
