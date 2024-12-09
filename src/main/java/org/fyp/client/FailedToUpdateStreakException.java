package org.fyp.client;

public class FailedToUpdateStreakException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to update streak";
    }
}
