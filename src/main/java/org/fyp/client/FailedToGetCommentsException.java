package org.fyp.client;

public class FailedToGetCommentsException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to get comments";
    }
}
