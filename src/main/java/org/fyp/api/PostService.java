package org.fyp.api;

import org.fyp.cli.Post;
import org.fyp.client.FailedToAddPostException;
import org.fyp.client.FailedToGetPostsException;
import org.fyp.db.PostDao;

import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class PostService {
    private PostDao postDao;

    public PostService(PostDao postDao) {
        this.postDao = postDao;
    }

    public void addPost(int user_id, String content, String title, InputStream imageInputStream, Date date_posted) throws FailedToAddPostException, SQLException {
        try{
            postDao.addPost(user_id, content, title, imageInputStream, date_posted);
        } catch (SQLException e) {
            throw new FailedToAddPostException();
        }
    }

    public List<Post> getAllPosts() throws FailedToGetPostsException, SQLException{
        try{
            return postDao.getAllPosts();
        } catch (SQLException e) {
            throw new FailedToGetPostsException();
        }
    }

}
