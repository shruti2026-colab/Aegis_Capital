package com.aegiscapital.exception;

public class ConcurrentTransactionException extends RuntimeException
{

    public ConcurrentTransactionException(String message){
        super(message);
    }
}


