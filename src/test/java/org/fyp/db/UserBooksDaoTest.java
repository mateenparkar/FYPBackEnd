package org.fyp.db;

import org.fyp.cli.UserBooks;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserBooksDaoTest {
    private DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    private Connection connection = Mockito.mock(Connection.class);
    private PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    private UserBooksDao userBooksDao = new UserBooksDao(databaseConnector);

    @Test
    public void addBookToUser_ShouldAddBookToUser_WhenValidBook() throws Exception {
        int userId = 1;
        int bookId = 1;

        String preparedStatement = "INSERT INTO UserBooks (user_id, book_id) VALUES (?, ?)";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);

        userBooksDao.addBookToUser(userId, bookId);

        Mockito.verify(statement, Mockito.times(1)).setInt(1, userId);
        Mockito.verify(statement, Mockito.times(1)).setInt(2, bookId);
    }

    @Test
    public void getUserBooks_ShouldReturnUserBooks_WhenUserHasBooks() throws Exception {
        int userId = 1;

        UserBooks expectedUserBook = new UserBooks(userId, 1);

        String preparedStatement = "SELECT * FROM UserBooks WHERE user_id = " + userId;

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt("user_id")).thenReturn(userId);
        Mockito.when(resultSet.getInt("book_id")).thenReturn(1);
        Mockito.when(resultSet.getString("read_status")).thenReturn("read_status");
        Mockito.when(resultSet.getInt("rating")).thenReturn(1);
        Mockito.when(resultSet.getDate("date_read")).thenReturn(null);

        List<UserBooks> userBooks = userBooksDao.getUserBooks(userId);

        assertEquals(expectedUserBook.getUser_id(), userBooks.get(0).getUser_id());
        assertEquals(expectedUserBook.getBook_id(), userBooks.get(0).getBook_id());
    }

    @Test
    public void getUserBooks_ShouldReturnEmptyList_WhenUserHasNoBooks() throws Exception {
        int userId = 1;

        String preparedStatement = "SELECT * FROM UserBooks WHERE user_id = " + userId;

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        List<UserBooks> userBooks = userBooksDao.getUserBooks(userId);

        assertTrue(userBooks.isEmpty());
    }

    @Test
    public void updateReadStatus_ShouldUpdateReadStatus_WhenValidBook() throws Exception {
        int userId = 1;
        int bookId = 1;
        String readStatus = "read_status";
        int rating = 1;
        java.sql.Date dateRead = null;

        String preparedStatement = "UPDATE UserBooks SET read_status = ?, rating = ?, date_read = ? WHERE user_id = ? AND book_id = ?";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);

        userBooksDao.updateReadStatus(userId, bookId, readStatus, rating, dateRead);

        Mockito.verify(statement, Mockito.times(1)).setString(1, readStatus);
        Mockito.verify(statement, Mockito.times(1)).setInt(2, rating);
        Mockito.verify(statement, Mockito.times(1)).setDate(3, dateRead);
        Mockito.verify(statement, Mockito.times(1)).setInt(4, userId);
        Mockito.verify(statement, Mockito.times(1)).setInt(5, bookId);
    }
}
