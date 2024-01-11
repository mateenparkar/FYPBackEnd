package org.fyp.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserBooksRequest {
    private int userId;
    private int bookId;


    @JsonCreator
    public UserBooksRequest(@JsonProperty("user_id") int userId, @JsonProperty("book_id") int bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }
}
