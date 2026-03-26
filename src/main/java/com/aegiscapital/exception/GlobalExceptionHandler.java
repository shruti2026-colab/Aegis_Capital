package com.aegiscapital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
    * handler to return the custom exception message
    */

    //exception to handle situation when account not found
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFound(AccountNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }


    @ExceptionHandler(ConcurrentTransactionException.class)
    public ResponseEntity<String> handleBalanceError(ConcurrentTransactionException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<String> handleBalanceError(IncorrectPasswordException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(IncorrectPINException.class)
    public ResponseEntity<String> handleBalanceError(IncorrectPINException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    //exception to handle situation when balance < amount
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleBalanceError(InsufficientBalanceException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    //exception to handle situation when balance < amount
    @ExceptionHandler(InvalidAccountNumberException.class)
    public ResponseEntity<String> handleBalanceError(InvalidAccountNumberException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    //exception to handle situation when balance < amount
    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<String> handleBalanceError(InvalidUserException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    //exception to handle situation when balance < amount
    @ExceptionHandler(NegativeNumberException.class)
    public ResponseEntity<String> handleBalanceError(NegativeNumberException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    //exception to handle situation when balance < amount
    @ExceptionHandler(SimilarEmailException.class)
    public ResponseEntity<String> handleBalanceError(SimilarEmailException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }


    @ExceptionHandler(UnauthorizedAccessException.class)
        @ResponseStatus(HttpStatus.FORBIDDEN)
        public String handleUnauthorizedAccess(UnauthorizedAccessException ex) {
            return ex.getMessage();
        }

}