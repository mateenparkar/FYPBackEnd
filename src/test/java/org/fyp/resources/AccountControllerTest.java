package org.fyp.resources;

import org.fyp.api.AccountService;
import org.fyp.cli.UpdateRequest;
import org.fyp.client.FailedToUpdatePasswordException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    AccountService accountServiceMock = Mockito.mock(AccountService.class);
    AccountController accountController = new AccountController(accountServiceMock);

    @Test
    public void updatePassword_ShouldReturn200_WhenUpdateSuccessful() {
        UpdateRequest updateRequest = new UpdateRequest(1, "password");

        Response response = accountController.updatePassword(updateRequest);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void updatePassword_ShouldReturn400_WhenUpdateFails() throws FailedToUpdatePasswordException, SQLException {
        UpdateRequest updateRequest = new UpdateRequest(1, "password");

        Mockito.doThrow(new FailedToUpdatePasswordException()).when(accountServiceMock).updatePassword(updateRequest);

        Response response = accountController.updatePassword(updateRequest);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void updatePassword_ShouldReturn500_WhenSQLExceptionThrown() throws FailedToUpdatePasswordException, SQLException {
        UpdateRequest updateRequest = new UpdateRequest(1, "password");

        Mockito.doThrow(new SQLException()).when(accountServiceMock).updatePassword(updateRequest);

        Response response = accountController.updatePassword(updateRequest);

        assertEquals(500, response.getStatus());
    }

}
