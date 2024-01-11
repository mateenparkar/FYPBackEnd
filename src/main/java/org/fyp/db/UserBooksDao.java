package org.fyp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserBooksDao {
    private DatabaseConnector databaseConnector;

    public UserBooksDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void addBookToUser(int userId, int bookId) throws SQLException {
        Connection c = databaseConnector.getConnection();

        PreparedStatement ps = c.prepareStatement("INSERT INTO UserBooks (user_id, book_id) " +
                "VALUES (?, ?)");

        ps.setInt(1, userId);
        ps.setInt(2, bookId);

        ps.executeUpdate();
    }
}
