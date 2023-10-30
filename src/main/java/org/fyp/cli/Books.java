package org.fyp.cli;

import java.util.Date;

public class Books {
    private int book_id;
    private String title;
    private int author;

    private String cover_image_url;

    private Date published_date;

    private int genre;

    private String description;


    public Books(int book_id, String title, int author, Date published_date, int genre, String description, String cover_image_url) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.published_date = published_date;
        this.genre = genre;
        this.description = description;
        this.cover_image_url = cover_image_url;
    }



    public int getBook_id() {
        return book_id;
    }

    public String getTitle() {
        return title;
    }

    public int getAuthor() {
        return author;
    }

    public Date getPublished_date() {
        return published_date;
    }

    public int getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public String getCover_image_url() {
        return cover_image_url;
    }



}
