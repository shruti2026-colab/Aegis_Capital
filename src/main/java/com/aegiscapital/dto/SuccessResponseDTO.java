package com.aegiscapital.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class SuccessResponseDTO<T> {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;

    public SuccessResponseDTO(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
