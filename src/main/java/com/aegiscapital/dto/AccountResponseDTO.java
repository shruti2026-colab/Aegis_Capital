package com.aegiscapital.dto;

import com.aegiscapital.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data

@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AccountResponseDTO {

    private String accountNumber;
    private BigDecimal balance;
    private String username;
    private String active;



}
