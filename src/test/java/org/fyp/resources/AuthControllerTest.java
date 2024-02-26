package org.fyp.resources;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.fyp.api.AuthService;
import org.fyp.cli.LoginRequest;
import org.fyp.cli.RegisterRequest;
import org.fyp.cli.User;
import org.fyp.client.FailedLoginException;
import org.fyp.client.FailedToRegisterException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.core.Response;
import java.security.Key;
import java.sql.SQLException;
import java.util.Date;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    AuthService authServiceMock = Mockito.mock(AuthService.class);
    AuthController authController = new AuthController(authServiceMock);

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Test
    public void login_ShouldReturn200_WhenLoginSuccessful() throws FailedLoginException, SQLException {
        int userId = 1;
        String email = "email@email.com";
        String username = "username";
        User mockUser = new User(userId, username, email, "password");

        String token = Jwts.builder()
                .setSubject(mockUser.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        LoginRequest loginRequest = new LoginRequest(username, "password");

        when(authServiceMock.login(loginRequest))
                .thenReturn(token);

        Response response = authController.login(loginRequest);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void login_ShouldReturn401_WhenLoginFails() throws SQLException, FailedLoginException {
        LoginRequest loginRequest = new LoginRequest("username", "password");

        when(authServiceMock.login(loginRequest))
                .thenThrow(new FailedLoginException());

        Response response = authController.login(loginRequest);

        assertEquals(401, response.getStatus());
    }

    @Test
    public void login_ShouldReturn500_WhenSQLExceptionIsThrown() throws SQLException, FailedLoginException {
        LoginRequest loginRequest = new LoginRequest("username", "password");

        when(authServiceMock.login(loginRequest))
                .thenThrow(new SQLException());

        Response response = authController.login(loginRequest);

        assertEquals(500, response.getStatus());
    }

    @Test
    public void register_ShouldReturn201_WhenRegistrationSuccessful() throws FailedToRegisterException {
        RegisterRequest registerRequest = new RegisterRequest("username", "email", "password");

        Response response = authController.register(registerRequest);

        assertEquals(201, response.getStatus());
    }

    @Test
    public void register_ShouldReturn400_WhenRegistrationFails() throws FailedToRegisterException, SQLException {
        RegisterRequest registerRequest = new RegisterRequest("username", "email", "password");
        Mockito.doThrow(new FailedToRegisterException()).when(authServiceMock).register(registerRequest);

        Response response = authController.register(registerRequest);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void register_ShouldReturn500_WhenSQLExceptionIsThrown() throws FailedToRegisterException, SQLException {
        RegisterRequest registerRequest = new RegisterRequest("username", "email", "password");
        Mockito.doThrow(new SQLException()).when(authServiceMock).register(registerRequest);

        Response response = authController.register(registerRequest);

        assertEquals(500, response.getStatus());
    }
}
