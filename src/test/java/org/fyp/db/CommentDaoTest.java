package org.fyp.db;

import org.fyp.cli.Comment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CommentDaoTest {
    private DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    private Connection connection = Mockito.mock(Connection.class);
    private PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    private CommentDao commentDao = new CommentDao(databaseConnector);

    @Test
    public void addComment_ShouldAddComment_WhenValid() throws Exception {
        int user_id = 1;
        int book_id = 1;
        String comment_text = "comment";
        Date date_posted = new Date(2020, 1, 1);

        String preparedStatement = "INSERT INTO Comments (user_id, book_id, comment_text, date_posted) VALUES (?, ?, ?, ?)";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);

        commentDao.addComment(user_id, book_id, comment_text, date_posted);

        Mockito.verify(statement, Mockito.times(1)).setInt(1, user_id);
        Mockito.verify(statement, Mockito.times(1)).setInt(2, book_id);
        Mockito.verify(statement, Mockito.times(1)).setString(3, comment_text);
        Mockito.verify(statement, Mockito.times(1)).setDate(4, date_posted);
        Mockito.verify(statement, Mockito.times(1)).executeUpdate();
    }

    @Test
    public void getCommentsByBook_ShouldReturnListOfComments_WhenBookExists() throws Exception {
        int id = 1;
        Comment expectedComment = new Comment(1, 1, "comment", new Date(2020, 1, 1));

        String preparedStatement = "SELECT * FROM Comments WHERE book_id = " + id;

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt("user_id")).thenReturn(1);
        Mockito.when(resultSet.getInt("book_id")).thenReturn(1);
        Mockito.when(resultSet.getString("comment_text")).thenReturn("comment");
        Mockito.when(resultSet.getDate("date_posted")).thenReturn(new Date(2020, 1, 1));

        List<Comment> comments =  commentDao.getCommentsByBook(id);

        assertEquals(expectedComment.getUser_id(), comments.get(0).getUser_id());
        assertEquals(expectedComment.getBook_id(), comments.get(0).getBook_id());
        assertEquals(expectedComment.getComment_text(), comments.get(0).getComment_text());
        assertEquals(expectedComment.getDate_posted(), comments.get(0).getDate_posted());
    }

    @Test
    public void getCommentsByBook_ShouldReturnEmptyList_WhenNoCommentsExist() throws Exception {
        int id = 1;
        String preparedStatement = "SELECT * FROM Comments WHERE book_id = " + id;

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        List<Comment> comments = commentDao.getCommentsByBook(id);

        assertTrue(comments.isEmpty());
    }

}
