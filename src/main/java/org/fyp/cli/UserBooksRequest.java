package org.fyp.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class UserBooksRequest {
    private int userId;
    private int bookId;

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDateRead() {
        return dateRead;
    }

    public void setDateRead(Date dateRead) {
        this.dateRead = dateRead;
    }

    private String readStatus;

    private int rating;

    private Date dateRead;


    public UserBooksRequest(int userId, int bookId) {
        this(userId, bookId, null, 0, null);
    }

    @JsonCreator
    public UserBooksRequest(@JsonProperty("user_id") int userId, @JsonProperty("book_id") int bookId, @JsonProperty("read_status") String readStatus, @JsonProperty("rating") int rating, @JsonProperty("date_read") Date dateRead) {
        this.userId = userId;
        this.bookId = bookId;
        this.readStatus = readStatus;
        this.rating = rating;
        this.dateRead = dateRead;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }
}
