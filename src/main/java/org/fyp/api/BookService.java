package org.fyp.api;

import org.fyp.cli.Books;
import org.fyp.client.FailedToGetBooksException;
import org.fyp.db.BookDao;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    private BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Books> getAllBooks() throws FailedToGetBooksException {
        try{
            return bookDao.getAllBooks();
        }catch(SQLException e){
            throw new FailedToGetBooksException();
        }

    }

    public Books getBookById(int id) throws FailedToGetBooksException {
        try{
            return bookDao.getBookById(id);
        }catch(SQLException e){
            throw new FailedToGetBooksException();
        }

    }

}
