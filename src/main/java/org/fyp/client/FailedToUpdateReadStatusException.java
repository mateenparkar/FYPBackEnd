package org.fyp.client;

public class FailedToUpdateReadStatusException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to update read status";
    }
}
