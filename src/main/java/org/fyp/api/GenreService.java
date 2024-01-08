package org.fyp.api;

import org.fyp.cli.Genre;
import org.fyp.client.FailedToGetGenreException;
import org.fyp.db.GenreDao;

import java.sql.SQLException;

public class GenreService {
    private GenreDao genreDao;

    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Genre getGenreById(int id) throws FailedToGetGenreException {
        try {
            Genre genre = genreDao.getGenreById(id);

            if (genre == null) {
                throw new FailedToGetGenreException();
            }

            return genre;
        } catch (SQLException e) {
            throw new FailedToGetGenreException();
        }
    }
}
