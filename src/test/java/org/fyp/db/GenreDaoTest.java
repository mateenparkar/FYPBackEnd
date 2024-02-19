package org.fyp.db;

import org.fyp.cli.Genre;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GenreDaoTest {
    private DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    private Connection connection = Mockito.mock(Connection.class);
    private PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    private GenreDao genreDao = new GenreDao(databaseConnector);


    @Test
    public void getGenreById_ShouldReturnGenre_WhenGenreExists() throws SQLException {
        int id = 1;
        String genreName = "genreName";
        Genre expectedGenre = new Genre(id, genreName);

        String preparedStatement = "SELECT genre_id, genre_name FROM BookGenres WHERE genre_id = " + id;

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(resultSet.getInt("genre_id")).thenReturn(id);
        Mockito.when(resultSet.getString("genre_name")).thenReturn(genreName);

        Genre genre = genreDao.getGenreById(id);

        assertEquals(expectedGenre.getGenre_id(), genre.getGenre_id());
        assertEquals(expectedGenre.getGenre_name(), genre.getGenre_name());
    }

    @Test
    public void getGenreById_ShouldReturnNull_WhenGenreDoesNotExist() throws SQLException {
        int id = 1;

        String preparedStatement = "SELECT genre_id, genre_name FROM BookGenres WHERE genre_id = " + id;

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        Genre genre = genreDao.getGenreById(id);

        assertNull(genre);
    }
}
