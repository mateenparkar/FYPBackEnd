package org.fyp.db;

import org.fyp.cli.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {
    private DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    private Connection connection = Mockito.mock(Connection.class);
    private PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    private UserDao userDao = new UserDao(databaseConnector);

    @Test
    public void getUserById_ShouldReturnUser_WhenUserExists() throws Exception {
        int id = 1;
        String username = "username";
        String email = "email";
        String password = "password";
        User expectedUser = new User(id, username, email, password);

        String preparedStatement = "SELECT * FROM Users WHERE user_id = " + id;
        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("user_id")).thenReturn(id);
        Mockito.when(resultSet.getString("username")).thenReturn(username);
        Mockito.when(resultSet.getString("email")).thenReturn(username);
        Mockito.when(resultSet.getString("password")).thenReturn(password);

        User user = userDao.getUserById(id);

        assertEquals(expectedUser.getUserId(), user.getUserId());
    }

    @Test
    public void getUserById_ShouldReturnNull_WhenUserDoesNotExist() throws Exception {
        int id = 1;
        String preparedStatement = "SELECT * FROM Users WHERE user_id = " + id;
        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        User user = userDao.getUserById(id);

        assertNull(user);
    }
}
