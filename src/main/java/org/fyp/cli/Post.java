package org.fyp.cli;

import java.sql.Date;

public class Post {
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate_posted(Date date_posted) {
        this.date_posted = date_posted;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    private int user_id;
    private String title;
    private String content;
    private Date date_posted;

    private byte[] imageBytes;

    public Post(int user_id, String title, String content, Date date_posted, byte[] imageBytes) {
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.date_posted = date_posted;
        this.imageBytes = imageBytes;
    }

    public int getUser_id() {
        return user_id;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDate_posted() {
        return date_posted;
    }
}
