package org.fyp.api;

import org.fyp.cli.UserBooksRequest;
import org.fyp.client.FailedToAddUserBooksException;
import org.fyp.db.UserBooksDao;

import java.sql.SQLException;

public class UserBooksService {
    private UserBooksDao userBooksDao;

    public UserBooksService(UserBooksDao userBooksDao) {
        this.userBooksDao = userBooksDao;
    }

    public void addBookToUser(UserBooksRequest userBooksRequest) throws FailedToAddUserBooksException, SQLException {
        try{
            userBooksDao.addBookToUser(userBooksRequest.getUserId(), userBooksRequest.getBookId());
        }catch(SQLException e){
            throw new FailedToAddUserBooksException();
        }
    }


}
