package org.fyp.client;

public class FailedToAddPostException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to add post";
    }
}
