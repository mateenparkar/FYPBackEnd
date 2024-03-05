package org.fyp.resources;

import org.fyp.api.UserService;
import org.fyp.cli.User;
import org.fyp.client.FailedToGetUserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    UserService userServiceMock = Mockito.mock(UserService.class);
    UserController userController = new UserController(userServiceMock);

    @Test
    public void getUserById_ShouldReturn200_WhenUserExists() throws FailedToGetUserException {
        int userId = 1;
        Mockito.when(userServiceMock.getUserById(userId)).thenReturn(new User(userId, "username", "email", "password"));

        Response response = userController.getUserById(userId);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void getUserById_ShouldReturn500_WhenUserDoesNotExist() throws FailedToGetUserException {
        int userId = 1;
        Mockito.when(userServiceMock.getUserById(userId)).thenThrow(new FailedToGetUserException());

        Response response = userController.getUserById(userId);

        assertEquals(500, response.getStatus());
    }
}
