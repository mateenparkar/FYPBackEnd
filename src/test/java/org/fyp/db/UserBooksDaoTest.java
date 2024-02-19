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

        UserBooks expectedUserBook = new UserBooks(userId, 1, "read_status", 1, null);

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
        assertEquals(expectedUserBook.getRead_status(), userBooks.get(0).getRead_status());
        assertEquals(expectedUserBook.getRating(), userBooks.get(0).getRating());
        assertEquals(expectedUserBook.getDate_read(), userBooks.get(0).getDate_read());
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
}
