package org.fyp.db;

import org.fyp.cli.Author;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthorDao {
    private DatabaseConnector databaseConnector;

    public AuthorDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public Author getAuthorById(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT author_id, name FROM Authors "
                + "WHERE author_id = " + id);

        if (rs.next()) {
            Author author = new Author(
                    rs.getInt("author_id"),
                    rs.getString("name")
            );

            return author;
        }

        return null;
    }
}
