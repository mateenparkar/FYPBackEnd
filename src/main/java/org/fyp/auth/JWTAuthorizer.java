package org.fyp.auth;

import io.dropwizard.auth.Authorizer;
import org.fyp.cli.User;

public class JWTAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User user, String role) {
        return user != null;
    }
}
