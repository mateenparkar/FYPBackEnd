package org.fyp.db;

import org.fyp.cli.Books;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private DatabaseConnector databaseConnector;

    public BookDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public List<Books> getAllBooks() throws SQLException {
        List<Books> books = new ArrayList<>();

        Connection c = databaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM Books");

        while(rs.next()){
            Books book = new Books(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getInt("author"),
                    rs.getDate("published_date"),
                    rs.getInt("genre"),
                    rs.getString("description"),
                    rs.getString("cover_image_url")
            );
            books.add(book);
        }
        return books;
    }

    public Books getBookById(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM Books WHERE book_id = " + id);

        if(rs.next()){
            Books book = new Books(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getInt("author"),
                    rs.getDate("published_date"),
                    rs.getInt("genre"),
                    rs.getString("description"),
                    rs.getString("cover_image_url")
            );
            return book;
        }
        return null;
    }

}
