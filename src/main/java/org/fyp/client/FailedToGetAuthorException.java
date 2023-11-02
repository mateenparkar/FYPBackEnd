package org.fyp.client;

public class FailedToGetAuthorException extends Throwable{
    @Override
    public String getMessage(){return "Failed to generate token.";}
}
