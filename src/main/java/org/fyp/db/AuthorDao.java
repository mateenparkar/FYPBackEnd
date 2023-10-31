package org.fyp.db;

import org.fyp.cli.Author;
import org.fyp.cli.Books;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
    private DatabaseConnector databaseConnector;

    public AuthorDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }


    public List<Author> getAllAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();

        Connection c = databaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM Authors");

        while(rs.next()){
            Author author = new Author(
                    rs.getInt("author_id"),
                    rs.getString("name")
            );
            authors.add(author);
        }
        return authors;
    }
}
