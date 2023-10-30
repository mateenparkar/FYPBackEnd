package org.fyp.client;

public class FailedToGetBooksException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to get books";
    }
}
