package org.fyp.client;

public class FailedToUpdatePasswordException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to update password";
    }
}
