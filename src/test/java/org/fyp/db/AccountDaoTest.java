package org.fyp.db;

import org.fyp.client.FailedLoginException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AccountDaoTest {
    private DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    private Connection connection = Mockito.mock(Connection.class);
    private PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    private AccountDao accountDao = new AccountDao(databaseConnector);

    @Test
    public void updatePassword_ShouldDoNothing_WhenInputIsValid() throws SQLException {
        int userId = 1;
        String password = "password";

        String preparedStatement = "UPDATE `Users` SET password = ? WHERE user_id = ?";
        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);

        accountDao.updatePassword(userId, password);

        Mockito.verify(statement, Mockito.times(1)).setString(1, password);
        Mockito.verify(statement, Mockito.times(1)).setInt(2, userId);
        Mockito.verify(statement, Mockito.times(1)).executeUpdate();
    }

    @Test
    public void updatePassword_ShouldThrowSQLException_WhenConnectionFails() throws SQLException {
        int userId = 1;
        String password = "password";

        String preparedStatement = "UPDATE `Users` SET password = ? WHERE user_id = ?";
        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> accountDao.updatePassword(userId, password));
    }
}
