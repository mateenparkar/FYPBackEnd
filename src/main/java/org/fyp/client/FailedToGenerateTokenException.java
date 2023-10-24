package org.fyp.client;

public class FailedToGenerateTokenException extends RuntimeException {
    @Override
    public String getMessage(){return "Failed to generate token.";}
}
