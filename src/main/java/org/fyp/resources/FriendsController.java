package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.FriendsService;
import org.fyp.cli.FriendRequest;
import org.fyp.client.FailedToAddFriendException;
import org.fyp.client.FailedToGetFriendsException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Friends API")
@Path("/api")
public class FriendsController {
    private FriendsService friendsService;
    public FriendsController(FriendsService friendsService){
        this.friendsService = friendsService;
    }

    @POST
    @Path("/addFriend")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFriend(FriendRequest friendRequest){
        try{
            friendsService.addFriend(friendRequest);
            return Response.status(Response.Status.CREATED).build();
        }catch(FailedToAddFriendException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }catch(SQLException e){
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/getFriends/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFriends(@PathParam("id") int user_id){
        try{
            return Response.ok(friendsService.getFriends(user_id)).build();
        }catch(SQLException | FailedToGetFriendsException e){
            return Response.serverError().build();
        }
    }
}
