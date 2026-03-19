package com.aegiscapital.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data

@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TransactionResponseDTO {

    private Long transactionId;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private String type;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd' T 'HH:mm:ss")
    private LocalDateTime timestamp;

}
