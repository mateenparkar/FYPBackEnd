package org.fyp.resources;

import org.fyp.api.UserBooksService;
import org.fyp.cli.UserBooks;
import org.fyp.cli.UserBooksRequest;
import org.fyp.client.BookAlreadyLikedException;
import org.fyp.client.FailedToAddUserBooksException;
import org.fyp.client.FailedToGetUserBooksException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserBooksControllerTest {
    UserBooksService userBooksServiceMock = Mockito.mock(UserBooksService.class);
    UserBooksController userBooksController = new UserBooksController(userBooksServiceMock);

    @Test
    public void addBookToUser_ShouldReturn200_WhenBookIsAdded() throws SQLException, FailedToAddUserBooksException, BookAlreadyLikedException {
        UserBooksRequest userBooksRequest = new UserBooksRequest(1, 1);
        Mockito.doNothing().when(userBooksServiceMock).addBookToUser(userBooksRequest);

        Response response = userBooksController.addBookToUser(userBooksRequest);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void addBookToUser_ShouldReturn400_WhenFailedToAddUserBooksExceptionIsThrown() throws SQLException, FailedToAddUserBooksException, BookAlreadyLikedException {
        UserBooksRequest userBooksRequest = new UserBooksRequest(1, 1);
        Mockito.doThrow(new FailedToAddUserBooksException()).when(userBooksServiceMock).addBookToUser(userBooksRequest);

        Response response = userBooksController.addBookToUser(userBooksRequest);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void getUserBooks_ShouldReturn200_WhenUserBooksExist() throws SQLException, FailedToGetUserBooksException {
        int userId = 1;
        List<UserBooks> userBooks = new ArrayList<>();
        Mockito.when(userBooksServiceMock.getUserBooks(userId)).thenReturn(userBooks);

        Response response = userBooksController.getUserBooks(userId);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void getUserBooks_ShouldReturn400_WhenFailedToGetUserBooksExceptionIsThrown() throws SQLException, FailedToGetUserBooksException {
        int userId = 1;
        Mockito.when(userBooksServiceMock.getUserBooks(userId)).thenThrow(new FailedToGetUserBooksException());

        Response response = userBooksController.getUserBooks(userId);
        assertEquals(400, response.getStatus());
    }
}
