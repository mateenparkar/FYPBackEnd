package org.fyp.auth;

import org.fyp.api.TokenService;
import org.fyp.cli.User;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.naming.AuthenticationException;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
@ExtendWith(MockitoExtension.class)
public class JWTAuthenticatorTest {
    TokenService tokenService = Mockito.mock(TokenService.class);
    JWTAuthenticator authenticator = new JWTAuthenticator(tokenService);

    @Test
    public void jwtAuthenticator_ShouldLogin_WhenValidToken() throws AuthenticationException, io.dropwizard.auth.AuthenticationException {
        String validToken = "validToken";
        User expectedUser = new User(2, "username", "email", "password");

        when(tokenService.isValidToken(validToken)).thenReturn(true);
        when(tokenService.decodeToken(validToken)).thenReturn(Optional.of(expectedUser));

        boolean result = authenticator.authenticate(validToken).isPresent();

        assertTrue(result);


        verify(tokenService).isValidToken(validToken);
        verify(tokenService).decodeToken(validToken);
    }

    @Test
    public  void jwtAuthenticator_ShouldNotValidateLogin_WhenInvalidToken() throws io.dropwizard.auth.AuthenticationException {
        String invalidToken = "invalidToken";
        when(tokenService.isValidToken(invalidToken)).thenReturn(false);

        Optional<User> result = authenticator.authenticate(invalidToken);

        assertFalse(result.isPresent());

        verify(tokenService).isValidToken(invalidToken);
        verify(tokenService, never()).decodeToken(any());
    }

    @Test
    public void jwtAuthenticator_ShouldThrowException_WhenFailedAuthentication(){
        String tokenWithException = "tokenWithException";
        when(tokenService.isValidToken(tokenWithException)).thenThrow(new RuntimeException("Test Exception"));
        assertThrows(io.dropwizard.auth.AuthenticationException.class, () -> authenticator.authenticate(tokenWithException));

        verify(tokenService).isValidToken(tokenWithException);
        verify(tokenService, never()).decodeToken(any());
    }
}
