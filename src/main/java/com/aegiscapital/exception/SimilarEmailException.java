package com.aegiscapital.exception;

public class SimilarEmailException extends RuntimeException {
    public SimilarEmailException(String message) {
        super(message);
    }
}
