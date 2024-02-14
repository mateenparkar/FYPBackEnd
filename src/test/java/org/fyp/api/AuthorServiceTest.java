package org.fyp.api;

import org.fyp.cli.Author;
import org.fyp.client.FailedToGetAuthorException;
import org.fyp.db.AuthorDao;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    AuthorDao authorDaoMock = Mockito.mock(AuthorDao.class);
    AuthorService authorService = new AuthorService(authorDaoMock);


    @Test
    public void getAuthorById_ShouldReturnAuthor_WhenAuthorExists() throws SQLException, FailedToGetAuthorException {
        int authorId = 2;
        String authorName = "author";

        Author mockAuthor = new Author(authorId, authorName);

        when(authorDaoMock.getAuthorById(authorId)).thenReturn(mockAuthor);

        Author resultAuthor = authorService.getAuthorById(authorId);

        assertNotNull(resultAuthor);
        assertEquals(mockAuthor, resultAuthor);
    }

    @Test
    public void getAuthorById_ShouldThrowException_WhenDaoThrowsException() throws SQLException {
        int authorId = 1;

        when(authorDaoMock.getAuthorById(authorId)).thenThrow(new SQLException());

        assertThrows(FailedToGetAuthorException.class, () -> authorService.getAuthorById(authorId));
    }

    @Test
    public void getAuthorById_ShouldThrowException_WhenAuthorIsNull() throws FailedToGetAuthorException, SQLException{
        int authorId = 1;

        when(authorDaoMock.getAuthorById(authorId)).thenReturn(null);

        assertThrows(FailedToGetAuthorException.class, () -> authorService.getAuthorById(authorId));
    }
}
