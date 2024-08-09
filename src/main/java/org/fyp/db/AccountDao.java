package org.fyp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDao {
    private DatabaseConnector databaseConnector;

    public AccountDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }


    public void updatePassword(int userId, String password) throws SQLException {
        Connection c = databaseConnector.getConnection();

        PreparedStatement ps = c.prepareStatement("UPDATE `Users` SET password = ? WHERE user_id = ?");
        ps.setString(1, password);
        ps.setInt(2, userId);
        ps.executeUpdate();

    }
}
