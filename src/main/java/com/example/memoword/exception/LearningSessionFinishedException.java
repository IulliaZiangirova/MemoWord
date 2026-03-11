package com.example.memoword.exception;

public class LearningSessionFinishedException extends RuntimeException{
    public LearningSessionFinishedException(String message) {
        super(message);
    }
}
