package org.fyp.resources;

import org.fyp.api.GenreService;
import org.fyp.cli.Genre;
import org.fyp.client.FailedToGetGenreException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.core.Response;

@ExtendWith(MockitoExtension.class)
public class GenreControllerTest {
    GenreService genreServiceMock = Mockito.mock(GenreService.class);
    GenreController genreController = new GenreController(genreServiceMock);

    @Test
    public void getGenreById_ShouldReturn200_WhenGenreExists() throws FailedToGetGenreException {
        int genreId = 1;
        Mockito.when(genreServiceMock.getGenreById(genreId)).thenReturn(new Genre(genreId, "name"));

        Response response = genreController.getGenreById(genreId);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void getGenreById_ShouldReturn500_WhenGenreDoesNotExist() throws FailedToGetGenreException {
        int genreId = 1;
        Mockito.when(genreServiceMock.getGenreById(genreId)).thenThrow(new FailedToGetGenreException());

        Response response = genreController.getGenreById(genreId);

        assertEquals(500, response.getStatus());
    }
}
