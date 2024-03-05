package org.fyp.client;

public class FailedToGetUserException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to get user";
    }
}
