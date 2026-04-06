package com.aegiscapital.exception;

import com.aegiscapital.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// This annotation makes this class handle exceptions globally across all controllers
@ControllerAdvice
public class GlobalExceptionHandler {

    // Common method to build consistent error response
    private ErrorResponseDTO buildError(HttpStatus status, String message, String path) {
        return new ErrorResponseDTO(
                status.value(),              // HTTP status code (e.g., 400, 404)
                status.getReasonPhrase(),    // HTTP status name (e.g., BAD_REQUEST)
                message,                     // Actual exception message
                path                         // API endpoint path
        );
    }

    // Account not found
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccountNotFound(
            AccountNotFoundException ex, HttpServletRequest req) {

        return new ResponseEntity<>(
                buildError(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI()),
                HttpStatus.NOT_FOUND
        );
    }

    // Invalid account number
    @ExceptionHandler(InvalidAccountNumberException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidAccount(
            InvalidAccountNumberException ex, HttpServletRequest req) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI()),
                HttpStatus.BAD_REQUEST
        );
    }

    // Invalid user
    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidUser(
            InvalidUserException ex, HttpServletRequest req) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI()),
                HttpStatus.BAD_REQUEST
        );
    }

    // User already logged in
    @ExceptionHandler(UserAlreadyLoggedInException.class)
    public ResponseEntity<ErrorResponseDTO> handleAlreadyLoggedIn(
            UserAlreadyLoggedInException ex, HttpServletRequest req) {

        return new ResponseEntity<>(
                buildError(HttpStatus.CONFLICT, ex.getMessage(), req.getRequestURI()),
                HttpStatus.CONFLICT
        );
    }

    // User not logged in
    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotLoggedIn(
            UserNotLoggedInException ex, HttpServletRequest req) {

        return new ResponseEntity<>(
                buildError(HttpStatus.UNAUTHORIZED, ex.getMessage(), req.getRequestURI()),
                HttpStatus.UNAUTHORIZED
        );
    }

    // Insufficient balance
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponseDTO> handleInsufficientBalance(
            InsufficientBalanceException ex, HttpServletRequest req) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI()),
                HttpStatus.BAD_REQUEST
        );
    }

    // Incorrect PIN
    @ExceptionHandler(IncorrectPINException.class)
    public ResponseEntity<ErrorResponseDTO> handleIncorrectPin(
            IncorrectPINException ex, HttpServletRequest req) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI()),
                HttpStatus.BAD_REQUEST
        );
    }

    // Negative amount
    @ExceptionHandler(NegativeNumberException.class)
    public ResponseEntity<ErrorResponseDTO> handleNegativeAmount(
            NegativeNumberException ex, HttpServletRequest req) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI()),
                HttpStatus.BAD_REQUEST
        );
    }

    // Concurrent transaction issue
    @ExceptionHandler(ConcurrentTransactionException.class)
    public ResponseEntity<ErrorResponseDTO> handleConcurrentTransaction(
            ConcurrentTransactionException ex, HttpServletRequest req) {

        return new ResponseEntity<>(
                buildError(HttpStatus.CONFLICT, ex.getMessage(), req.getRequestURI()),
                HttpStatus.CONFLICT
        );
    }


    // Unauthorized access
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(
            UnauthorizedAccessException ex, HttpServletRequest req) {

        return new ResponseEntity<>(
                buildError(HttpStatus.FORBIDDEN, ex.getMessage(), req.getRequestURI()),
                HttpStatus.FORBIDDEN
        );
    }


    // Catch all unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(
            Exception ex, HttpServletRequest req) {

        return new ResponseEntity<>(
                buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), req.getRequestURI()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}