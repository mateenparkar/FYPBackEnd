package org.fyp.api;

import io.jsonwebtoken.SignatureAlgorithm;
import org.fyp.cli.AuthRole;
import org.fyp.cli.LoginRequest;
import org.fyp.cli.RegisterRequest;
import org.fyp.cli.User;
import org.fyp.client.FailedLoginException;
import org.fyp.client.FailedToGenerateTokenException;
import org.fyp.client.FailedToRegisterException;
import org.fyp.db.AuthDao;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.sql.SQLException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    AuthDao authDaoMock = Mockito.mock(AuthDao.class);
    TokenService tokenService = Mockito.mock(TokenService.class);
    AuthService authService = new AuthService(authDaoMock, tokenService);
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Test
    public void login_ShouldReturnString_WhenUserIsValid() throws Exception, FailedLoginException {
        int userId = 2;
        String email = "email@email.com";
        String username = "user";
        String password = "$2a$09$NNN97Lre5WJPO5I7tQAKOOIfXNnkG4u.bDXlYlV2W3kS0wDEsuCyK";

        User mockUser = new User(userId, username,email, password);

        String mockToken = "token";
        LoginRequest mockLoginRequest = new LoginRequest(username, "password");
        when(authDaoMock.getUserByEmail(mockLoginRequest.getUsername())).thenReturn(mockUser);
        when(tokenService.generateToken(mockUser)).thenReturn(mockToken);

        String result = authService.login(mockLoginRequest);

        assertNotNull(result);
        assertEquals("token", result);
    }

    @Test
    public void login_ShouldThrowException_WhenDaoReturnsNull() throws Exception, FailedLoginException {
        String username = "username";

        LoginRequest mockLoginRequest = new LoginRequest(username, "password");

        when(authDaoMock.getUserByEmail(mockLoginRequest.getUsername())).thenReturn(null);


        assertThrows(FailedLoginException.class, () -> authService.login(mockLoginRequest));
    }

    @Test
    public void login_ShouldThrowException_WhenDaoThrowsException() throws SQLException {
        String username = "username";

        LoginRequest mockLoginRequest = new LoginRequest(username, "password");

        when(authDaoMock.getUserByEmail(mockLoginRequest.getUsername())).thenThrow(SQLException.class);

        assertThrows(FailedToGenerateTokenException.class, () -> authService.login(mockLoginRequest));
    }

    @Test
    public void register_ShouldThrowFailedToRegisterException_WhenDaoThrowsException() throws SQLException {

        RegisterRequest registerRequest = new RegisterRequest("username", "email@example.com", "password");

        Mockito.doThrow(new SQLException("Simulated SQLException")).when(authDaoMock).register(anyString(), anyString(), anyString());
        assertThrows(FailedToRegisterException.class, () -> {
            authService.register(registerRequest);
        });
    }

    @Test
    public void register_ShouldDoNothing_WhenInputIsValid() {
        RegisterRequest validRequest = new RegisterRequest("user", "user@user.com","password");

        assertDoesNotThrow(() -> authService.register(validRequest));
    }

}
