package org.fyp.client;

public class FailedToGetAuthorsException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to get authors";
    }
}
