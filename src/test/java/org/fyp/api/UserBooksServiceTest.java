package org.fyp.api;

import org.fyp.cli.UserBooks;
import org.fyp.cli.UserBooksRequest;
import org.fyp.client.FailedToAddUserBooksException;
import org.fyp.client.FailedToGetUserBooksException;
import org.fyp.client.FailedToUpdateReadStatusException;
import org.fyp.db.UserBooksDao;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserBooksServiceTest {
    UserBooksDao userBooksDaoMock = Mockito.mock(UserBooksDao.class);
    UserBooksService userBooksService = new UserBooksService(userBooksDaoMock);

    @Test
    public void addBookToUser_ShouldDoNothing_WhenBookIsAdded() {
        UserBooksRequest userBooksRequest = new UserBooksRequest(1, 1);
        assertDoesNotThrow(() -> userBooksService.addBookToUser(userBooksRequest));
    }

    @Test
    public void addBookToUser_ShouldThrowException_WhenDaoThrowsException() throws SQLException {
        UserBooksRequest userBooksRequest = new UserBooksRequest(1, 1);
        Mockito.doThrow(new SQLException()).when(userBooksDaoMock).addBookToUser(1, 1);
        assertThrows(FailedToAddUserBooksException.class, () -> userBooksService.addBookToUser(userBooksRequest));
    }

    @Test
    public void getUserBooks_ShouldReturnList_WhenBooksAreFound() throws SQLException, FailedToGetUserBooksException {
        List<UserBooks> mockBooksList = new ArrayList<>();
        mockBooksList.add(new UserBooks(1, 1, "", 1, new Date()));
        int userId = 1;
        when(userBooksDaoMock.getUserBooks(1)).thenReturn(mockBooksList);
        List<UserBooks> result = userBooksService.getUserBooks(userId);
        assertEquals(mockBooksList, result);
    }

    @Test
    public void getUserBooks_ShouldThrowException_WhenDaoThrowsException() throws SQLException {
        int userId = 1;
        when(userBooksDaoMock.getUserBooks(userId)).thenThrow(SQLException.class);
        assertThrows(FailedToGetUserBooksException.class, () -> userBooksService.getUserBooks(userId));
    }

    @Test
    public void updateReadStatus_ShouldDoNothing_WhenReadStatusIsUpdated() {
        UserBooksRequest userBooksRequest = new UserBooksRequest(1, 1, "read", 5, new java.sql.Date(2021, 1, 1));
        assertDoesNotThrow(() -> userBooksService.updateReadStatus(userBooksRequest));
    }

    @Test
    public void updateReadStatus_ShouldThrowException_WhenDaoThrowsException() throws SQLException {
        UserBooksRequest userBooksRequest = new UserBooksRequest(1, 1, "read", 5, new java.sql.Date(2021, 1, 1));
        Mockito.doThrow(new SQLException()).when(userBooksDaoMock).updateReadStatus(1,1, "read", 5, new java.sql.Date(2021, 1, 1));
        assertThrows(FailedToUpdateReadStatusException.class, () -> userBooksService.updateReadStatus(userBooksRequest));
    }
}
