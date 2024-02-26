package org.fyp.resources;

import org.fyp.api.AuthorService;
import org.fyp.cli.Author;
import org.fyp.client.FailedToGetAuthorException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.core.Response;

@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {
    AuthorService authorServiceMock = Mockito.mock(AuthorService.class);
    AuthorController authorController = new AuthorController(authorServiceMock);

    @Test
    public void getAuthorById_ShouldReturn200_WhenAuthorExists() throws FailedToGetAuthorException {
        int authorId = 1;
        Mockito.when(authorServiceMock.getAuthorById(authorId)).thenReturn(new Author(authorId, "name"));

        Response response = authorController.getAuthorById(authorId);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void getAuthorById_ShouldReturn500_WhenAuthorDoesNotExist() throws FailedToGetAuthorException {
        int authorId = 1;
        Mockito.when(authorServiceMock.getAuthorById(authorId)).thenThrow(new FailedToGetAuthorException());

        Response response = authorController.getAuthorById(authorId);

        assertEquals(500, response.getStatus());
    }
}
