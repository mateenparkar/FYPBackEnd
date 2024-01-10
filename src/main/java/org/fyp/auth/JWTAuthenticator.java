package org.fyp.auth;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.fyp.api.TokenService;
import org.fyp.cli.User;

import java.util.Optional;

public class JWTAuthenticator implements Authenticator<String, User> {
    private final TokenService tokenService;

    public JWTAuthenticator(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Optional<User> authenticate(String token)  throws AuthenticationException {
        try {
            boolean isTokenValid = tokenService.isValidToken(token);

            if (isTokenValid) {
                Optional<User> user = tokenService.decodeToken(token);

                return user;
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new AuthenticationException("Failed to authenticate", e);
        }
    }

}
