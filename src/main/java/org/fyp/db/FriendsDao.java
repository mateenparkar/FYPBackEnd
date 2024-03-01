package org.fyp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
