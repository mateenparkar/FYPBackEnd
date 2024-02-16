package org.fyp.db;

import org.fyp.cli.Books;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class BookDaoTest {
    private DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    private Connection connection = Mockito.mock(Connection.class);
    private PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    private BookDao bookDao = new BookDao(databaseConnector);

    @Test
    public void getAllBooks_ShouldReturnListOfBooks_WhenIdIsValid() throws SQLException {
        Books expectedBook = new Books(1, "Book1", 1, new Date(), 1, "string1", "string2");

        String preparedStatement = "SELECT * FROM Books";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt("book_id")).thenReturn(1);
        Mockito.when(resultSet.getString("title")).thenReturn("Book1");

        List<Books> books = bookDao.getAllBooks();

        assertEquals(expectedBook.getBook_id(), books.get(0).getBook_id());
        assertEquals(expectedBook.getTitle(), books.get(0).getTitle());
    }

    @Test
    public void getAllBooks_ShouldReturnEmptyList_WhenNoBooksExist() throws SQLException {
        String preparedStatement = "SELECT * FROM Books";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        List<Books> books = bookDao.getAllBooks();

        assertTrue(books.isEmpty());
    }

    @Test
    public void getBookById_ShouldReturnBook_WhenBookExists() throws SQLException {
        int id = 1;
        Books expectedBook = new Books(id, "Book1", 1, new Date(), 1, "string1", "string2");

        String preparedStatement = "SELECT * FROM Books WHERE book_id = " + id;
        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("book_id")).thenReturn(id);
        Mockito.when(resultSet.getString("title")).thenReturn("Book1");

        Books book = bookDao.getBookById(id);

        assertEquals(expectedBook.getBook_id(), book.getBook_id());
        assertEquals(expectedBook.getTitle(), book.getTitle());
    }

    @Test
    public void getBookById_ShouldReturnNull_WhenBookDoesNotExist() throws SQLException {
        int id = 1;

        String preparedStatement = "SELECT * FROM Books WHERE book_id = " + id;
        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        Books book = bookDao.getBookById(id);

        assertNull(book);
    }
}
