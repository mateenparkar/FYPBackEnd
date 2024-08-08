package org.fyp.api;

import org.fyp.cli.Post;
import org.fyp.cli.UserBooks;
import org.fyp.client.FailedToAddPostException;
import org.fyp.client.FailedToGetPostsException;
import org.fyp.db.PostDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.powermock.api.mockito.PowerMockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    PostDao postDaoMock = Mockito.mock(PostDao.class);
    PostService postService = new PostService(postDaoMock);

    @Test
    public void addPost_ShouldDoNothing_WhenPostIsAdded() {
        assertDoesNotThrow(() -> postService.addPost(1, "content", "title", null, null));
    }

    @Test
    public void addPost_ShouldThrowException_WhenDaoThrowsException() throws Exception {
        Mockito.doThrow(new SQLException()).when(postDaoMock).addPost(1, "content", "title", null, null);
        assertThrows(FailedToAddPostException.class, () -> postService.addPost(1, "content", "title", null, null));
    }

    @Test
    public void getAllPosts_ShouldReturnList_WhenPostsAreFound() throws Exception, FailedToGetPostsException {
        List<Post> mockPostsList = new ArrayList<>();
        mockPostsList.add(new Post(1, "title", "", null, null));
        when(postDaoMock.getAllPosts()).thenReturn(mockPostsList);
        List<Post> result = postService.getAllPosts();
        assertEquals(mockPostsList, result);
    }

    @Test
    public void getAllPosts_ShouldThrowException_WhenDaoThrowsException() throws Exception {
        when(postDaoMock.getAllPosts()).thenThrow(SQLException.class);
        assertThrows(FailedToGetPostsException.class, () -> postService.getAllPosts());
    }

}
