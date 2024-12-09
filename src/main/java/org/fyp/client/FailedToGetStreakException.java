package org.fyp.client;

public class FailedToGetStreakException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to get streak";
    }
}
