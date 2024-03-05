package org.fyp.api;

import org.fyp.cli.User;
import org.fyp.client.FailedToGetUserException;
import org.fyp.db.UserDao;

import java.sql.SQLException;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(int id) throws FailedToGetUserException {
        try {
            User user = userDao.getUserById(id);

            if (user == null) {
                throw new FailedToGetUserException();
            }

            return user;
        } catch (SQLException e) {
            throw new FailedToGetUserException();
        }
    }
}
