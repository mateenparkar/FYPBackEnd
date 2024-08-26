package org.fyp.validator;

import org.fyp.cli.RegisterRequest;
import org.fyp.db.AuthDao;

import java.sql.SQLException;

public class RegisterValidator {

    public RegisterValidator(AuthDao authDao) {
        this.authDao = authDao;
    }

    private AuthDao authDao;
    public String validateUser(RegisterRequest user) throws SQLException {
        String password = user.getPassword();
        String username = user.getUsername();

        if(authDao.getUserByEmail(username) != null) {
            return "User with this username already exists.";
        }

        if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        }

        if (!password.matches(".*[A-Z].*")) {
            return "Password must contain at least one upper case letter.";
        }

        if (!password.matches(".*[a-z].*")) {
            return "Password must contain at least one lower case letter.";
        }

        // Check if password contains at least one special character
        if (!password.matches(".*[@#$%^&+=].*")) {
            return "Password must contain at least one special character (@#$%^&+=).";
        }

        return "";
    }
}
