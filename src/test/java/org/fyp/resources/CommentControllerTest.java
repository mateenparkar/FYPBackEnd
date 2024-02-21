package org.fyp.resources;

import org.fyp.api.CommentService;
import org.fyp.cli.Comment;
import org.fyp.cli.CommentRequest;
import org.fyp.client.FailedToAddCommentException;
import org.fyp.client.FailedToGetCommentsException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {
    CommentService commentServiceMock = Mockito.mock(CommentService.class);
    CommentController commentController = new CommentController(commentServiceMock);

    @Test
    public void addComment_ShouldReturn201_WhenCommentIsAdded() throws FailedToAddCommentException, SQLException {
        CommentRequest commentRequest = new CommentRequest(1, 1, "comment", new Date(1));

        Mockito.doNothing().when(commentServiceMock).addComment(commentRequest);

        Response response = commentController.addComment(commentRequest);

        assertEquals(201, response.getStatus());
    }

    @Test
    public void addComment_ShouldReturn400_WhenFailedToAddCommentExceptionIsThrown() throws FailedToAddCommentException, SQLException {
        CommentRequest commentRequest = new CommentRequest(1, 1, "comment", new Date(1));

        Mockito.doThrow(new FailedToAddCommentException()).when(commentServiceMock).addComment(commentRequest);

        Response response = commentController.addComment(commentRequest);

        assertEquals(400, response.getStatus());
    }
    @Test
    public void addComment_ShouldReturn500_WhenCommentIsNotAdded() throws FailedToAddCommentException, SQLException {
        CommentRequest commentRequest = new CommentRequest(1, 1, "comment", new Date(1));

        Mockito.doThrow(new SQLException()).when(commentServiceMock).addComment(commentRequest);

        Response response = commentController.addComment(commentRequest);

        assertEquals(500, response.getStatus());
    }

    @Test
    public void getCommentsByBook_ShouldReturn200_WhenCommentsExist() throws FailedToGetCommentsException {
        int bookId = 1;

        List<Comment> expectedComments = new ArrayList<>();

        Mockito.when(commentServiceMock.getCommentsByBook(bookId)).thenReturn(expectedComments);

        Response response = commentController.getCommentsByBook(bookId);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void getCommentsByBook_ShouldReturn500_WhenCommentsDoNotExist() throws FailedToGetCommentsException {
        int bookId = 1;

        Mockito.when(commentServiceMock.getCommentsByBook(bookId)).thenThrow(new FailedToGetCommentsException());

        Response response = commentController.getCommentsByBook(bookId);

        assertEquals(500, response.getStatus());
    }
}
