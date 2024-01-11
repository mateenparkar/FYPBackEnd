package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.UserBooksService;
import org.fyp.cli.UserBooksRequest;
import org.fyp.client.FailedToAddUserBooksException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
        }catch(FailedToAddUserBooksException | SQLException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
