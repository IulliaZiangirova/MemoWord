package com.example.memoword.exception;

public class SessionDoesntExistException extends RuntimeException{
    public SessionDoesntExistException(String message) {
        super(message);
    }
}
