package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.UserService;
import org.fyp.client.FailedToGetUserException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("User API")
@Path("/api")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id){
        try{
            return Response.ok(userService.getUserById(id)).build();
        }catch(FailedToGetUserException e){
            return Response.serverError().build();
        }
    }

}
