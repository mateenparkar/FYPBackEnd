package org.fyp.db;

import org.fyp.cli.Author;
import org.fyp.cli.Genre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GenreDao {
    private DatabaseConnector databaseConnector;

    public GenreDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public Genre getGenreById(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT genre_id, genre_name FROM BookGenres "
                + "WHERE genre_id = " + id);

        if (rs.next()) {
            Genre genre = new Genre(
                    rs.getInt("genre_id"),
                    rs.getString("genre_name")
            );

            return genre;
        }

        return null;
    }
}
