package org.fyp.db;

import org.fyp.cli.Books;
import org.fyp.cli.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Comment> getCommentsByBook(int id) throws SQLException{
        List<Comment> comments = new ArrayList<>();
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Comments WHERE book_id = " + id);

        while(rs.next()){
            Comment comment = new Comment(
                    rs.getInt("user_id"),
                    rs.getInt("book_id"),
                    rs.getString("comment_text"),
                    rs.getDate("date_posted")
            );
            comments.add(comment);
        }
        return comments;
    }
}
