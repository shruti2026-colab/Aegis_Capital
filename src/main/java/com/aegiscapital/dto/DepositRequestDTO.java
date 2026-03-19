package com.aegiscapital.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequestDTO {

        private String accountNumber;
        private BigDecimal amount;
}
