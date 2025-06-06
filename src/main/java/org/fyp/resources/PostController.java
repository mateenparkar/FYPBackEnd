package org.fyp.resources;

import io.swagger.annotations.Api;
import javassist.bytecode.SourceFileAttribute;
import org.fyp.api.PostService;
import org.fyp.client.FailedToAddPostException;
import org.fyp.client.FailedToGetPostsException;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
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
            System.out.println(user_id + " " + content + " " + title + " " + imageInputStream + " " + date_posted);
            postService.addPost(user_id, content, title, imageInputStream, date_posted);
            return Response.status(Response.Status.CREATED).build();
        } catch (FailedToAddPostException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/getPosts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts(){
        try{
            return Response.ok(postService.getAllPosts()).build();
        }catch(FailedToGetPostsException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }catch (SQLException e){
            return Response.serverError().build();
        }
    }



}
