package com.socialmediaplatform.Exceptions.CustomException;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
