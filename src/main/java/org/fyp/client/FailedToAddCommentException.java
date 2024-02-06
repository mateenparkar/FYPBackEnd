package org.fyp.client;

public class FailedToAddCommentException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to add comment";
    }
}
