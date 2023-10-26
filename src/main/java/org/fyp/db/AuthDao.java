package org.fyp.db;

import io.dropwizard.auth.Auth;
import org.fyp.api.TokenService;
import org.fyp.cli.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao {
    private DatabaseConnector databaseConnector;

    public AuthDao(DatabaseConnector databaseConnector){
        this.databaseConnector = databaseConnector;
    }

    public User getUserByEmail(String username, String password) throws SQLException {
        Connection c = databaseConnector.getConnection();

        PreparedStatement ps = c.prepareStatement("SELECT user_id, username, email, password FROM Users WHERE username = '" + username + "'");
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            if(BCrypt.checkpw(password, rs.getString("password"))){
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                return user;
            }
        }
        return null;
    }

}
