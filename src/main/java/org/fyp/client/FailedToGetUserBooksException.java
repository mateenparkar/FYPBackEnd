package org.fyp.client;

public class FailedToGetUserBooksException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to get user books";
    }
}
