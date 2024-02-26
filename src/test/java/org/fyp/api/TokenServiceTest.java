package org.fyp.api;

import org.fyp.cli.User;
import org.fyp.db.AuthDao;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {
    AuthDao authDaoMock = Mockito.mock(AuthDao.class);
    TokenService tokenService = new TokenService(authDaoMock);

    @Test
    public void decodeToken_ShouldDecodeToken_IfValidToken() throws SQLException {
        User expectedUser = new User(1, "username", "email", "password");
        String validToken = tokenService.generateToken(expectedUser);

        when(authDaoMock.getUserByEmail(anyString())).thenReturn(expectedUser);

        Optional<User> decodedUser = tokenService.decodeToken(validToken);

        assertTrue(decodedUser.isPresent());
        assertEquals(expectedUser, decodedUser.get());
    }

    @Test
    public void decodeToken_ShouldFail_IfInvalidToken(){
        String invalidToken = "invalidToken";

        Optional<User> decodedUser = tokenService.decodeToken(invalidToken);

        assertFalse(decodedUser.isPresent());
    }

    @Test
    public void isValidToken_ShouldReturnTrue_IfTokenValid(){
        User expectedUser = new User(1, "username", "email", "password");
        String validToken = tokenService.generateToken(expectedUser);

        boolean isValid = tokenService.isValidToken(validToken);

        assertTrue(isValid);
    }

    @Test
    public void isValidToken_ShouldReturnFalse_IfTokenInvalid(){
        String invalidToken = "invalidToken";

        boolean isValid = tokenService.isValidToken(invalidToken);

        assertFalse(isValid);
    }

}
