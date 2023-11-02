package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.AuthorService;
import org.fyp.client.FailedToGetAuthorException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    @Path("/author/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorById(@PathParam("id") int id){
        try{
            return Response.ok(authorService.getAuthorById(id)).build();
        }catch(FailedToGetAuthorException e){
            return Response.serverError().build();
        }
    }
}
