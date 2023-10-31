package org.fyp.api;

import org.fyp.cli.Author;
import org.fyp.cli.Books;
import org.fyp.client.FailedToGetAuthorsException;
import org.fyp.client.FailedToGetBooksException;
import org.fyp.db.AuthorDao;

import java.sql.SQLException;
import java.util.List;

public class AuthorService {
    private AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public List<Author> getAllAuthors() throws FailedToGetAuthorsException {
        try{
            return authorDao.getAllAuthors();
        }catch(SQLException e){
            throw new FailedToGetAuthorsException();
        }

    }
}
