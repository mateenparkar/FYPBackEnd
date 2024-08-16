package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.UserBooksService;
import org.fyp.cli.UserBooksRequest;
import org.fyp.client.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
@Api("UserBooks API")
@Path("/api")
public class UserBooksController {
    private UserBooksService userBooksService;

    public UserBooksController(UserBooksService userBooksService) {
        this.userBooksService = userBooksService;
    }


    @POST
    @Path("/addBookToUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBookToUser(UserBooksRequest userBooksRequest) {
        try{
            userBooksService.addBookToUser(userBooksRequest);
            return Response.ok().build();
        }catch(FailedToAddUserBooksException | SQLException | BookAlreadyLikedException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/getUserBooks/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserBooks(@PathParam("userId") int userId) {
        try{
            return Response.ok(userBooksService.getUserBooks(userId)).build();
        }catch(FailedToGetUserBooksException | SQLException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/deleteBookFromUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBookFromUser(UserBooksRequest userBooksRequest) {
        try{
            userBooksService.deleteBookFromUser(userBooksRequest);
            return Response.ok().build();
        }catch(SQLException | FailedToDeleteUserBookException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/updateBookForUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBookForUser(UserBooksRequest userBooksRequest) {
        try{
            userBooksService.updateReadStatus(userBooksRequest);
            return Response.ok().build();
        }catch(SQLException | FailedToUpdateReadStatusException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
