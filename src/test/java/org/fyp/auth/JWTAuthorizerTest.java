package org.fyp.auth;

import org.fyp.cli.User;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JWTAuthorizerTest {
    JWTAuthorizer jwtAuthorizer = new JWTAuthorizer();


    @Test
    public void jwtAuthorizer_ShouldReturnTrue_WhenUserIsValid(){
        User user = new User(1, "username", "email", "password");

        assertTrue(jwtAuthorizer.authorize(user, "string"));
    }

    @Test
    public void jwtAuthorizer_ShouldReturnFalse_WhenUserIsNotValid(){
        User user = null;

        assertFalse(jwtAuthorizer.authorize(user, "string"));
    }
}
