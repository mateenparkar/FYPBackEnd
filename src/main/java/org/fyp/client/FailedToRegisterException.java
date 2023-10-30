package org.fyp.client;

public class FailedToRegisterException extends Throwable {
    @Override
    public String getMessage() {
        return "Failed to register";
    }
}
