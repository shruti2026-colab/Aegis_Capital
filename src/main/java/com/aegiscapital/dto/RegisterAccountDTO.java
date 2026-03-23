package com.aegiscapital.dto;

import com.aegiscapital.entity.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegisterAccountDTO {

    private BigDecimal amount;
    private String userId;
    private String pin;
}
