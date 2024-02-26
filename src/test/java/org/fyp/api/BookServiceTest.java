package org.fyp.api;

import org.fyp.cli.Books;
import org.fyp.client.FailedToGetBooksException;
import org.fyp.db.BookDao;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.print.Book;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    BookDao bookDaoMock = Mockito.mock(BookDao.class);

    BookService bookService = new BookService(bookDaoMock);

    @Test
    public void getAllBooks_shouldGetAllBooks() throws FailedToGetBooksException, SQLException {
        List<Books> mockBooksList = new ArrayList<>();
        mockBooksList.add(new Books(1, "Book1", 1, new Date(), 1, "string1", "string2"));
        mockBooksList.add(new Books(2, "Book1", 2, new Date(), 2, "string3", "string4"));

        when(bookDaoMock.getAllBooks()).thenReturn(mockBooksList);

        List<Books> result = bookService.getAllBooks();

        assertEquals(mockBooksList, result);
    }

    @Test
    public void getBookById_shouldGetBookById_WhenIdIsValid() throws FailedToGetBooksException, SQLException {
        int bookId = 1;
        Books mockBook = new Books(1, "Book1", 1, new Date(), 1, "string1", "string2");

        when(bookDaoMock.getBookById(bookId)).thenReturn(mockBook);

        Books result = bookService.getBookById(bookId);

        assertEquals(mockBook, result);
    }

    @Test
    public void getAllBooks_ShouldThrowException_WhenDaoThrowsException() throws SQLException {
        when(bookDaoMock.getAllBooks()).thenThrow(SQLException.class);

        assertThrows(FailedToGetBooksException.class, () -> bookService.getAllBooks());
    }

    @Test
    public void getBookById_ShouldThrowException_WhenDaoThrowsException() throws SQLException{
        int bookId = 1;
        when(bookDaoMock.getBookById(bookId)).thenThrow(SQLException.class);
        assertThrows(FailedToGetBooksException.class, () -> bookService.getBookById(bookId));
    }
}
