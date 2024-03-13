package org.fyp.client;

public class FailedToGetPostsException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to get posts";
    }
}
