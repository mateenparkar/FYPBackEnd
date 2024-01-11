package org.fyp.client;

public class FailedToAddUserBooksException extends Throwable {
    @Override
    public String toString() {
        return "Failed to add user and books to database";
    }
}
