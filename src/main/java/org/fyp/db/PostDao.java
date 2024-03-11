package org.fyp.db;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostDao {
    private DatabaseConnector databaseConnector;

    public PostDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void addPost(int user_id, String content, String title, InputStream imageInputStream, Date date_posted) throws SQLException {
        try (Connection c = databaseConnector.getConnection()) {
            byte[] imageBytes = IOUtils.toByteArray(imageInputStream);

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
}
