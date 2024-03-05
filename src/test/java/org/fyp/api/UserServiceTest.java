package org.fyp.api;

import org.fyp.cli.User;
import org.fyp.client.FailedToGetUserException;
import org.fyp.db.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    UserDao userDaoMock = Mockito.mock(UserDao.class);
    UserService userService = new UserService(userDaoMock);


    @Test
    public void getUserById_ShouldReturnUser_WhenUserExists() throws SQLException, FailedToGetUserException {
        int userId = 1;
        User expectedUser = new User(userId, "username", "password", "email");
        when(userDaoMock.getUserById(userId)).thenReturn(expectedUser);

        User resultUser = userService.getUserById(userId);

        assertEquals(expectedUser, resultUser);
    }

    @Test
    public void getUserById_ShouldThrowException_WhenDaoThrowsException() throws SQLException {
        int userId = 1;
        when(userDaoMock.getUserById(userId)).thenThrow(SQLException.class);

        assertThrows(FailedToGetUserException.class, () -> userService.getUserById(userId));
    }

}
