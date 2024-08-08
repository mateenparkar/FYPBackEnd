package org.fyp.db;

import org.fyp.cli.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PostDaoTest {
    private DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    private Connection connection = Mockito.mock(Connection.class);
    private PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    private PostDao postDao = new PostDao(databaseConnector);

    @Test
    public void addPost_ShouldAddPost_WhenValidPost() throws Exception {
        int userId = 1;
        String content = "content";
        String title = "title";
        InputStream imageInputStream = new ByteArrayInputStream(new byte[]{1, 2, 3, 4});
        Date datePosted = new Date(System.currentTimeMillis());

        String preparedStatement = "INSERT INTO Posts (user_id, post_image_url, title, content, date_posted) VALUES (?, ?, ?, ?, ?)";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.prepareStatement(preparedStatement)).thenReturn(statement);
        Mockito.when(statement.executeUpdate()).thenReturn(1);

        postDao.addPost(userId, content, title, imageInputStream, datePosted);

        Mockito.verify(statement, Mockito.times(1)).setInt(1, userId);
        Mockito.verify(statement, Mockito.times(1)).setString(3, title);
        Mockito.verify(statement, Mockito.times(1)).setString(4, content);
        Mockito.verify(statement, Mockito.times(1)).setDate(5, datePosted);
    }

    @Test
    public void getAllPosts_ShouldReturnAllPosts_WhenPostsExist() throws Exception {
        int userId = 1;
        String title = "title";
        String content = "content";
        Date datePosted = new Date(System.currentTimeMillis());
        byte[] imageBytes = new byte[]{1, 2, 3, 4};

        Post expectedPost = new Post(userId, title, content, datePosted, imageBytes);

        String preparedStatement = "SELECT * FROM Posts";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt("user_id")).thenReturn(userId);
        Mockito.when(resultSet.getString("title")).thenReturn(title);
        Mockito.when(resultSet.getString("content")).thenReturn(content);
        Mockito.when(resultSet.getDate("date_posted")).thenReturn(datePosted);
        Mockito.when(resultSet.getBytes("post_image_url")).thenReturn(imageBytes);

        List<Post> posts = postDao.getAllPosts();

        assertEquals(1, posts.size());
        assertEquals(expectedPost.getUser_id(), posts.get(0).getUser_id());
        assertEquals(expectedPost.getTitle(), posts.get(0).getTitle());
        assertEquals(expectedPost.getContent(), posts.get(0).getContent());
        assertEquals(expectedPost.getDate_posted(), posts.get(0).getDate_posted());
        assertEquals(expectedPost.getImageBytes(), posts.get(0).getImageBytes());
    }

    @Test
    public void getAllPosts_ShouldReturnEmptyList_WhenNoPostsExist() throws Exception {
        String preparedStatement = "SELECT * FROM Posts";

        DatabaseConnector.setConn(connection);
        Mockito.when(connection.createStatement()).thenReturn(statement);
        Mockito.when(statement.executeQuery(preparedStatement)).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(false);

        List<Post> posts = postDao.getAllPosts();

        assertTrue(posts.isEmpty());
    }


}
