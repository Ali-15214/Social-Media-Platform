package com.socialmediaplatform.Exceptions.CustomException;

public class ActionAlreadyPerformedException extends RuntimeException {
    public ActionAlreadyPerformedException(String message) {
        super(message);
    }
}