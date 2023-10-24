package org.fyp.cli;

public class User {
    public User(int userId, String username, String email, String hashedPassword) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    private final int userId;
    private final String username;

    private final String email;

    private String hashedPassword;


}
