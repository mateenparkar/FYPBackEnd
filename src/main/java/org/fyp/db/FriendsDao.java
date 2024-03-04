package org.fyp.db;

import org.fyp.cli.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendsDao {
    private DatabaseConnector databaseConnector;

    public FriendsDao(DatabaseConnector databaseConnector){
        this.databaseConnector = databaseConnector;
    }

    public void addFriend(int user_id, int friend_id) throws SQLException {
        Connection c = databaseConnector.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO Friends (user_id, friend_user_id) VALUES (?, ?)");
        ps.setInt(1, user_id);
        ps.setInt(2, friend_id);

        ps.executeUpdate();
    }

    public List<String> getFriends(int user_id) throws SQLException {
        Connection c = databaseConnector.getConnection();
        List<String> friendsUsernames = new ArrayList<>();
        PreparedStatement ps = c.prepareStatement("SELECT u.username " +
                "FROM Friends f " +
                "JOIN Users u ON f.friend_user_id = u.user_id " +
                "WHERE f.user_id = ?");

        ps.setInt(1, user_id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String friendUsername = rs.getString("username");
            friendsUsernames.add(friendUsername);
        }
        return friendsUsernames;
    }
}
