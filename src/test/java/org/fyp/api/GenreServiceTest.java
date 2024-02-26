package org.fyp.api;

import org.fyp.cli.Genre;
import org.fyp.client.FailedToGetGenreException;
import org.fyp.db.GenreDao;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {
    GenreDao genreDaoMock = Mockito.mock(GenreDao.class);
    GenreService genreService = new GenreService(genreDaoMock);

    @Test
    public void getGenreById_ShouldReturnGenre_WhenGenreExists() throws FailedToGetGenreException, SQLException{
        int genreId = 1;
        Genre expectedGenre = new Genre(genreId, "genre");

        when(genreDaoMock.getGenreById(genreId)).thenReturn(expectedGenre);

        Genre resultGenre = genreService.getGenreById(genreId);

        assertEquals(expectedGenre, resultGenre);
    }

    @Test
    public void getGenreById_ShouldThrowException_WhenDaoThrowsException() throws SQLException{
        int genreId = 1;

        when(genreDaoMock.getGenreById(genreId)).thenThrow(SQLException.class);

        assertThrows(FailedToGetGenreException.class, () -> genreService.getGenreById(genreId));
    }
}
