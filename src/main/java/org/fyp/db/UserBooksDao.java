package org.fyp.db;

import org.fyp.cli.Books;
import org.fyp.cli.UserBooks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void deleteBookFromUser(int userId, int bookId) throws SQLException {
        Connection c = databaseConnector.getConnection();

        PreparedStatement ps = c.prepareStatement("DELETE FROM UserBooks WHERE user_id = ? AND book_id = ?");

        ps.setInt(1, userId);
        ps.setInt(2, bookId);

        ps.executeUpdate();
    }

    public List<UserBooks> getUserBooks(int userId) throws SQLException {
        List<UserBooks> userBooks = new ArrayList<>();

        Connection c = databaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM UserBooks WHERE user_id = " + userId);


        while(rs.next()) {
            UserBooks userBook = new UserBooks(
                    rs.getInt("user_id"),
                    rs.getInt("book_id"),
                    rs.getString("read_status"),
                    rs.getInt("rating"),
                    rs.getDate("date_read")
            );
            userBooks.add(userBook);
        }

        return userBooks;
    }
}
