package org.fyp.api;

import org.fyp.cli.Comment;
import org.fyp.cli.CommentRequest;
import org.fyp.client.FailedToAddCommentException;
import org.fyp.client.FailedToGetCommentsException;
import org.fyp.db.CommentDao;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    CommentDao commentDaoMock = Mockito.mock(CommentDao.class);

    CommentService commentService = new CommentService(commentDaoMock);

    @Test
    public void addComment_ShouldDoNothing_WhenCommentIsAdded() throws SQLException, FailedToAddCommentException{
        CommentRequest commentRequest = new CommentRequest(1, 1, "comment", new Date(2021, 1, 1));
        assertDoesNotThrow(() -> commentService.addComment(commentRequest));
    }

    @Test
    public void addComment_ShouldThrowException_WhenDaoThrowsException() throws SQLException {
        CommentRequest commentRequest = new CommentRequest(1, 1, "comment", new Date(2021, 1, 1));
        doThrow(SQLException.class).when(commentDaoMock).addComment(1, 1, "comment", new Date(2021, 1, 1));
        assertThrows(FailedToAddCommentException.class, () -> commentService.addComment(commentRequest));
    }

    @Test
    public void getCommentsByBook_ShouldReturnAllBooks_WhenIdIsValid() throws SQLException, FailedToGetCommentsException{
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1, 1, "comment", new Date(2021, 1, 1)));
        when(commentDaoMock.getCommentsByBook(123)).thenReturn(comments);
        List<Comment> retrievedComments = commentService.getCommentsByBook(123);
        assertEquals(comments, retrievedComments);
    }

    @Test
    public void getCommentsByBook_ShouldThrowException_WhenDaoThrowsException() throws SQLException{
        when(commentDaoMock.getCommentsByBook(123)).thenThrow(SQLException.class);
        assertThrows(FailedToGetCommentsException.class, () -> commentService.getCommentsByBook(123));
    }
}
