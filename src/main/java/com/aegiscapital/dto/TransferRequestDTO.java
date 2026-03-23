package com.aegiscapital.dto;

import lombok.Data;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


@Data
public class TransferRequestDTO {

    @NotNull(message = "Sender account ID is required")
    private String fromAccountNumber;

    @NotNull(message = "Receiver account Id is required")
    private String toAccountNumber;

    @NotNull(message = "Transfer amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

}
