package org.fyp.client;

public class FailedToGetFriendsException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to get friends";
    }
}
