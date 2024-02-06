package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.CommentService;
import org.fyp.cli.CommentRequest;
import org.fyp.client.FailedToAddCommentException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Comments API")
@Path("/api")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }


    @POST
    @Path("/comment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComment(CommentRequest commentRequest){
        try{
            commentService.addComment(commentRequest);
            return Response.status(Response.Status.CREATED).build();
        }catch(FailedToAddCommentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }catch(SQLException e){
            return Response.serverError().build();
        }
    }
}
