package org.fyp.cli;


public class Author {
    private int author_id;
    private String name;

    public Author(int author_id, String name) {
        this.author_id = author_id;
        this.name = name;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public String getName() {
        return name;
    }


}
