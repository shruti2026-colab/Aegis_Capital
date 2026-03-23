package com.aegiscapital.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}
