package org.fyp.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class CommentRequest {
    @JsonCreator
    public CommentRequest(@JsonProperty("user_id") int user_id, @JsonProperty("book_id") int book_id, @JsonProperty("comment_text") String comment_text, @JsonProperty("date_posted") Date date_posted) {
        this.user_id = user_id;
        this.book_id = book_id;
        this.comment_text = comment_text;
        this.date_posted = date_posted;
    }

    private int user_id;
    private int book_id;
    private String comment_text;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public Date getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(Date date_posted) {
        this.date_posted = date_posted;
    }

    private Date date_posted;

}
