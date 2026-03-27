package com.aegiscapital.service;

import com.aegiscapital.dto.AccountResponseDTO;
import com.aegiscapital.dto.DepositRequestDTO;
import com.aegiscapital.dto.RegisterAccountDTO;
import com.aegiscapital.dto.WithdrawRequestDTO;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService
{
    void deposit(DepositRequestDTO request);
    void withdraw(WithdrawRequestDTO request);
    BigDecimal getBalance(String accountNumber);
    String openAccount(RegisterAccountDTO request);
    AccountResponseDTO getDetails(String accountNumber);

    List<AccountResponseDTO> getAccountsByUserId(String userId);

}
