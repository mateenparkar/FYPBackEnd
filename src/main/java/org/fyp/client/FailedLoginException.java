package org.fyp.client;

public class FailedLoginException extends RuntimeException{
    @Override
    public String getMessage(){return "Failed to login.";}
}
