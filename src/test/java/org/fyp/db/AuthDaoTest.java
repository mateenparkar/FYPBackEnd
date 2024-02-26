package org.fyp.db;

import org.fyp.cli.LoginRequest;
import org.fyp.cli.User;
import org.fyp.client.FailedLoginException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class AuthDaoTest {
    private DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    private Connection connection = Mockito.mock(Connection.class);
    private PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    private AuthDao authDao = new AuthDao(databaseConnector);

    @Test
    public void validLogin_ShouldReturnUser_WhenLoginRequestIsValid() throws SQLException, FailedLoginException {
        String username = "username";
        String salt = BCrypt.gensalt();
        LoginRequest validLoginRequest = new LoginRequest(username, "password");

        User expectedUser = new User(1, username, "email", "password");

        String preparedStatement = "SELECT user_id, username, email, password FROM Users WHERE username = '" + username + "'";
        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("user_id")).thenReturn(1);
        Mockito.when(resultSet.getString("username")).thenReturn("username");
        Mockito.when(resultSet.getString("email")).thenReturn("email");
        Mockito.when(resultSet.getString("password")).thenReturn(BCrypt.hashpw("password", salt));

        User user = authDao.getUserByEmail(validLoginRequest.getUsername());

        assertEquals(expectedUser.getUserId(), user.getUserId());
        assertEquals(expectedUser.getEmail(), user.getEmail());
    }

    @Test
    public void invalidLogin_ShouldThrowFailedLoginException_WhenLoginRequestIsInvalid() throws SQLException {
        String username = "username";
        LoginRequest invalidLoginRequest = new LoginRequest(username, "password");

        String preparedStatement = "SELECT user_id, username, email, password FROM Users WHERE username = '" + username + "'";
        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(FailedLoginException.class);

        assertThrows(FailedLoginException.class,
                () -> authDao.getUserByEmail(invalidLoginRequest.getUsername()));
    }

    @Test
    public void validLogin_ShouldThrowSQlException_WhenSQLExceptionIsThrown() throws SQLException {
        String username = "username";
        LoginRequest validLoginRequest = new LoginRequest(username, "password");

        String preparedStatement = "SELECT user_id, username, email, password FROM Users WHERE username = '" + username + "'";
        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> authDao.getUserByEmail(validLoginRequest.getUsername()));
    }

    @Test
    public void register_ShouldInsertUser_WhenRegisterRequestIsValid() throws SQLException {
        String username = "username";
        String email = "email";
        String password = "password";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getString("username")).thenReturn(username);

        authDao.register(username, email, password);
        assertTrue(resultSet.next());

        assertEquals(username, resultSet.getString("username"));
    }
}
