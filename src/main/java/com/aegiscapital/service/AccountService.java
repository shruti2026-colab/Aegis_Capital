package com.aegiscapital.service;

import com.aegiscapital.dto.AccountResponseDTO;
import com.aegiscapital.dto.DepositRequestDTO;
import com.aegiscapital.dto.WithdrawRequestDTO;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService
{
    void deposit(DepositRequestDTO request);
    void withdraw(WithdrawRequestDTO request);
    BigDecimal getBalance(String accountNumber);

    AccountResponseDTO getDetails(String accountNumber);

    List<AccountResponseDTO> getAccountsByUserId(String userId);

}
