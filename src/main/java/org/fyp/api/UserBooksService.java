package org.fyp.api;

import org.fyp.cli.ReadBooks;
import org.fyp.cli.UserBooks;
import org.fyp.cli.UserBooksRequest;
import org.fyp.client.*;
import org.fyp.db.UserBooksDao;

import java.sql.SQLException;
import java.util.List;

public class UserBooksService {
    private UserBooksDao userBooksDao;

    public UserBooksService(UserBooksDao userBooksDao) {
        this.userBooksDao = userBooksDao;
    }

    public boolean isBookLikedByUser(int userId, int bookId) throws SQLException {
        List<UserBooks> userBooks = userBooksDao.getUserBooks(userId);
        for (UserBooks userBook : userBooks) {
            if (userBook.getBook_id() == bookId) {
                return true;
            }
        }
        return false;
    }

    public void addBookToUser(UserBooksRequest userBooksRequest) throws FailedToAddUserBooksException, SQLException, BookAlreadyLikedException {
        int userId = userBooksRequest.getUserId();
        int bookId = userBooksRequest.getBookId();

        if (isBookLikedByUser(userId, bookId)) {
            throw new BookAlreadyLikedException();
        }

        try{
            userBooksDao.addBookToUser(userBooksRequest.getUserId(), userBooksRequest.getBookId());
        }catch(SQLException e){
            throw new FailedToAddUserBooksException();
        }
    }

    public void deleteBookFromUser(UserBooksRequest userBooksRequest) throws SQLException, FailedToDeleteUserBookException {
        int userId = userBooksRequest.getUserId();
        int bookId = userBooksRequest.getBookId();

        try{
            userBooksDao.deleteBookFromUser(userId, bookId);
        }catch(SQLException e){
            throw new FailedToDeleteUserBookException();
        }


    }

    public List<UserBooks> getUserBooks(int userId) throws SQLException, FailedToGetUserBooksException {
        try{
            return userBooksDao.getUserBooks(userId);
        }catch(SQLException e){
            throw new FailedToGetUserBooksException();
        }
    }

    public void updateReadStatus(UserBooksRequest userBooksRequest) throws SQLException, FailedToUpdateReadStatusException {
        try{
            userBooksDao.updateReadStatus(userBooksRequest.getUserId(), userBooksRequest.getBookId(), userBooksRequest.getReadStatus(), userBooksRequest.getRating(), userBooksRequest.getDateRead());
        }catch(SQLException e){
            System.out.println(e.getMessage());
            throw new FailedToUpdateReadStatusException();
        }
    }

    public boolean hasUserReadBook(int userId, int bookId) throws SQLException {
        List<ReadBooks> userBooks = userBooksDao.getReadBooks(userId);
        for (ReadBooks userBook : userBooks) {
            if (userBook.getBook_id() == bookId && "Read".equalsIgnoreCase(userBook.getRead_status())) {
                return true;
            }
        }
        return false;
    }


}
