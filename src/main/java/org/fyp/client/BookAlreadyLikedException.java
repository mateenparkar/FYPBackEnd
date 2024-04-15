package org.fyp.client;

public class BookAlreadyLikedException extends Throwable{
    @Override
    public String getMessage() {
        return "The book is already liked by the user.";
    }
}
