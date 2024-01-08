package org.fyp.client;

public class FailedToGetGenreException extends Throwable {
    @Override
    public String getMessage(){return "Failed to get genre.";}
}
