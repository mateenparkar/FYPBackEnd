package org.fyp.resources;

import org.fyp.api.BookService;
import org.fyp.cli.Books;
import org.fyp.client.FailedToGetBooksException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BooksControllerTest {
    BookService bookServiceMock = Mockito.mock(BookService.class);
    BooksController booksController = new BooksController(bookServiceMock);


    @Test
    public void getAllBooks_ShouldReturn200_WhenBooksExist() throws FailedToGetBooksException {
        List<Books> expectedBooks = new ArrayList<>();

        Mockito.when(bookServiceMock.getAllBooks()).thenReturn(expectedBooks);

        Response response = booksController.getAllBooks();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void getAllBooks_ShouldReturn500_WhenBooksDoNotExist() throws FailedToGetBooksException {
        Mockito.when(bookServiceMock.getAllBooks()).thenThrow(new FailedToGetBooksException());

        Response response = booksController.getAllBooks();

        assertEquals(500, response.getStatus());
    }

    @Test
    public void getBookById_ShouldReturn200_WhenBookExists() throws FailedToGetBooksException {
        int bookId = 1;
        Mockito.when(bookServiceMock.getBookById(bookId)).thenReturn(new Books(bookId, "name", 1, new Date(), 1, "description",
                "image"));

        Response response = booksController.getBookById(bookId);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void getBookById_ShouldReturn500_WhenBookDoesNotExist() throws FailedToGetBooksException {
        int bookId = 1;
        Mockito.when(bookServiceMock.getBookById(bookId)).thenThrow(new FailedToGetBooksException());

        Response response = booksController.getBookById(bookId);

        assertEquals(500, response.getStatus());
    }
}
