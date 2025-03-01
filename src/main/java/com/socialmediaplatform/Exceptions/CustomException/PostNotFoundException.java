package com.socialmediaplatform.Exceptions.CustomException;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
