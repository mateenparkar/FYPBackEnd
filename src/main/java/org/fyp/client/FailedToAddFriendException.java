package org.fyp.client;

public class FailedToAddFriendException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to add friend";
    }
}
