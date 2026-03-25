package com.aegiscapital.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountAdminDTO {
    private String accountNumber;
    private BigDecimal balance;
    private String userEmail;
}
