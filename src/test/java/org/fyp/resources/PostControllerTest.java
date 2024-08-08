package org.fyp.resources;

import org.fyp.api.PostService;
import org.fyp.cli.Post;
import org.fyp.client.FailedToAddPostException;
import org.fyp.client.FailedToGetPostsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
    PostService postServiceMock = Mockito.mock(PostService.class);
    PostController postController = new PostController(postServiceMock);


    @Test
    public void addPost_ShouldReturn201_WhenPostIsAdded() throws FailedToAddPostException, SQLException {
        int userId = 1;
        String content = "content";
        String title = "title";
        InputStream imageInputStream = new ByteArrayInputStream(new byte[]{1, 2, 3, 4});
        Date datePosted = new Date(System.currentTimeMillis());

        Mockito.doNothing().when(postServiceMock).addPost(userId, content, title, imageInputStream, datePosted);

        Response response = postController.addPost(userId, imageInputStream, title, content, datePosted);
        assertEquals(201, response.getStatus());
    }

    @Test
    public void addPost_ShouldReturn400_WhenFailedToAddPostExceptionIsThrown() throws FailedToAddPostException, SQLException {
        int userId = 1;
        String content = "content";
        String title = "title";
        InputStream imageInputStream = new ByteArrayInputStream(new byte[]{1, 2, 3, 4});
        Date datePosted = new Date(System.currentTimeMillis());

        Mockito.doThrow(new FailedToAddPostException()).when(postServiceMock).addPost(userId, content, title, imageInputStream, datePosted);

        Response response = postController.addPost(userId, imageInputStream, title, content, datePosted);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void addPost_ShouldReturn500_WhenSQLExceptionIsThrown() throws FailedToAddPostException, SQLException {
        int userId = 1;
        String content = "content";
        String title = "title";
        InputStream imageInputStream = new ByteArrayInputStream(new byte[]{1, 2, 3, 4});
        Date datePosted = new Date(System.currentTimeMillis());

        Mockito.doThrow(new SQLException()).when(postServiceMock).addPost(userId, content, title, imageInputStream, datePosted);

        Response response = postController.addPost(userId, imageInputStream, title, content, datePosted);
        assertEquals(500, response.getStatus());
    }

    @Test
    public void getAllPosts_ShouldReturn200_WhenPostsExist() throws SQLException, FailedToGetPostsException {
        List<Post> posts = new ArrayList<>();
        Mockito.when(postServiceMock.getAllPosts()).thenReturn(posts);

        Response response = postController.getAllPosts();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void getAllPosts_ShouldReturn400_WhenFailedToGetPostsExceptionIsThrown() throws SQLException, FailedToGetPostsException {
        Mockito.when(postServiceMock.getAllPosts()).thenThrow(new FailedToGetPostsException());

        Response response = postController.getAllPosts();
        assertEquals(400, response.getStatus());
    }
}
