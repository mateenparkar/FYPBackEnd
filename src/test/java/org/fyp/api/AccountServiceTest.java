package org.fyp.api;

import org.fyp.cli.UpdateRequest;
import org.fyp.client.FailedToUpdatePasswordException;
import org.fyp.db.AccountDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    AccountDao accountDaoMock = Mockito.mock(AccountDao.class);
    AccountService accountService = new AccountService(accountDaoMock);


    @Test
    public void updatePassword_ShouldDoNothing_WhenUpdateRequestIsValid(){
        int userId = 2;
        String password = "password";

        UpdateRequest updateRequest = new UpdateRequest(userId, password);

        assertDoesNotThrow(() -> accountService.updatePassword(updateRequest));
    }

    @Test
    public void updatePassword_ShouldThrowFailedToUpdateException_WhenDaoThrowsException() throws SQLException {
        int userId = 2;
        String password = "password";

        UpdateRequest updateRequest = new UpdateRequest(userId, password);

        Mockito.doThrow(new SQLException()).when(accountDaoMock).updatePassword(Mockito.eq(updateRequest.getUserId()), Mockito.anyString());

        assertThrows(FailedToUpdatePasswordException.class, () -> accountService.updatePassword(updateRequest));
    }
}
