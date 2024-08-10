package org.fyp.api;

import org.fyp.cli.UpdateRequest;
import org.fyp.client.FailedToUpdatePasswordException;
import org.fyp.db.AccountDao;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class AccountService {
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void updatePassword(UpdateRequest updateRequest) throws FailedToUpdatePasswordException, SQLException {
        String salt = BCrypt.gensalt(9);

        String hashedPassword = BCrypt.hashpw(updateRequest.getPassword(), salt);
        try {
            accountDao.updatePassword(updateRequest.getUserId(), hashedPassword);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToUpdatePasswordException();
        }
    }
}
