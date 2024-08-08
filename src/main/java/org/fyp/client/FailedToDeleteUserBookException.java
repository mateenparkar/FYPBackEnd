package org.fyp.client;

public class FailedToDeleteUserBookException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to delete user book";
    }
}
