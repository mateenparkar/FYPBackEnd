package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.PostService;
import org.fyp.client.FailedToAddPostException;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;

@Api("Posts API")
@Path("/api")
public class PostController {
    private PostService postService;
    public PostController(PostService postService){
        this.postService = postService;
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPost(
            @FormDataParam("user_id") int user_id,
            @FormDataParam("post_image_url") InputStream imageInputStream,
            @FormDataParam("title") String title,
            @FormDataParam("content") String content,
            @FormDataParam("date_posted") Date date_posted) {
        try {
            postService.addPost(user_id, content, title, imageInputStream, date_posted);
            return Response.status(Response.Status.CREATED).build();
        } catch (FailedToAddPostException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }



}
