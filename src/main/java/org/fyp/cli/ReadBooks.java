package org.fyp.cli;

import java.util.Date;

public class ReadBooks {
    public ReadBooks(int user_id, int book_id, String read_status, int rating, Date date_read) {
        this.user_id = user_id;
        this.book_id = book_id;
        this.read_status = read_status;
        this.rating = rating;
        this.date_read = date_read;
    }

    int user_id;
    int book_id;
    String read_status;
    int rating;

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

    public String getRead_status() {
        return read_status;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate_read() {
        return date_read;
    }

    public void setDate_read(Date date_read) {
        this.date_read = date_read;
    }

    Date date_read;
}
