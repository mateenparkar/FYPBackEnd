package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.GenreService;
import org.fyp.client.FailedToGetGenreException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Genre API")
@Path("/api")
public class GenreController {
    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GET
    @Path("/genre/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGenreById(@PathParam("id") int id){
        try{
            return Response.ok(genreService.getGenreById(id)).build();
        }catch(FailedToGetGenreException e){
            return Response.serverError().build();
        }
    }

}
