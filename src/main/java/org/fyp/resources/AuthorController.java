package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.AuthorService;
import org.fyp.client.FailedToGetAuthorsException;
import org.fyp.client.FailedToGetBooksException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Author API")
@Path("/api")
public class AuthorController {
    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GET
    @Path("/authors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors(){
        try{
            return Response.ok(authorService.getAllAuthors()).build();
        }catch(FailedToGetAuthorsException e){
            return Response.serverError().build();

        }
    }
}
