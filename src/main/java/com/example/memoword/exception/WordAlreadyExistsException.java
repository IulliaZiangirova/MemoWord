package com.example.memoword.exception;

public class WordAlreadyExistsException extends RuntimeException {

    public WordAlreadyExistsException(String message) {
        super(message);
    }
}
