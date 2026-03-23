package com.aegiscapital.service;

import com.aegiscapital.dto.DepositRequestDTO;
import com.aegiscapital.dto.WithdrawRequestDTO;

import java.math.BigDecimal;

public interface AccountService
{
    void deposit(DepositRequestDTO request);
    void withdraw(WithdrawRequestDTO request);
    BigDecimal getBalance(String accountNumber);

}
