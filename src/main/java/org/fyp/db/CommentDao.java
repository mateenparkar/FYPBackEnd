package org.fyp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class CommentDao {
    private DatabaseConnector databaseConnector;

    public CommentDao(DatabaseConnector databaseConnector){
        this.databaseConnector = databaseConnector;
    }
    public void addComment(int user_id, int book_id, String comment_text, Date date_posted) throws SQLException {
        Connection c = databaseConnector.getConnection();
        PreparedStatement ps = c.prepareStatement("INSERT INTO Comments (user_id, book_id, comment_text, date_posted) VALUES (?, ?, ?, ?)");
        ps.setInt(1, user_id);
        ps.setInt(2, book_id);
        ps.setString(3, comment_text);
        ps.setDate(4, date_posted);

        ps.executeUpdate();


    }
}
