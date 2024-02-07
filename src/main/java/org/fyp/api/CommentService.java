package org.fyp.api;

import org.fyp.cli.Comment;
import org.fyp.cli.CommentRequest;
import org.fyp.client.FailedToAddCommentException;
import org.fyp.client.FailedToGetCommentsException;
import org.fyp.db.CommentDao;

import java.sql.SQLException;
import java.util.List;

public class CommentService {
    private CommentDao commentDao;

    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public void addComment(CommentRequest commentRequest) throws FailedToAddCommentException, SQLException {
        try {
            commentDao.addComment(commentRequest.getUser_id(), commentRequest.getBook_id(), commentRequest.getComment_text(), commentRequest.getDate_posted());
        } catch (SQLException e) {
            throw new FailedToAddCommentException();
        }
    }

    public List<Comment> getCommentsByBook(int id) throws FailedToGetCommentsException {
        try{
            return commentDao.getCommentsByBook(id);
        }catch(SQLException e){
            throw new FailedToGetCommentsException();
        }
    }
}
