package org.fyp.client;

public class FailedLoginException extends Throwable{
    @Override
    public String getMessage(){return "Failed to login.";}
}
