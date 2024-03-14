package org.fyp.db;

import org.apache.commons.io.IOUtils;
import org.fyp.cli.Post;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDao {
    private DatabaseConnector databaseConnector;

    public PostDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void addPost(int user_id, String content, String title, InputStream imageInputStream, Date date_posted) throws SQLException {
        try (Connection c = databaseConnector.getConnection()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = imageInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();


            String query = "INSERT INTO Posts (user_id, post_image_url, title, content, date_posted) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = c.prepareStatement(query)) {
                ps.setInt(1, user_id);
                ps.setBytes(2, imageBytes);
                ps.setString(3, title);
                ps.setString(4, content);
                ps.setDate(5, date_posted);

                ps.executeUpdate();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Post> getAllPosts() throws SQLException {
        List<Post> posts = new ArrayList<>();
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Posts");

        while(rs.next()){
            Post post = new Post(
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getDate("date_posted"),
                    rs.getBytes("post_image_url")
            );
            posts.add(post);
        }
        return posts;
    }


}
