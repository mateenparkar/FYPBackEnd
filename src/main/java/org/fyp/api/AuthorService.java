package org.fyp.api;

import org.fyp.cli.Author;
import org.fyp.client.FailedToGetAuthorException;
import org.fyp.db.AuthorDao;

import java.sql.SQLException;

public class AuthorService {
    private AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public Author getAuthorById(int id) throws FailedToGetAuthorException {
        try {
            Author author = authorDao.getAuthorById(id);

            if (author == null) {
                throw new FailedToGetAuthorException();
            }

            return author;
        } catch (SQLException e) {
            throw new FailedToGetAuthorException();
        }
    }
}
