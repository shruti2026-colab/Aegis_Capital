package com.aegiscapital.service;

import com.aegiscapital.dto.TransactionResponseDTO;
import com.aegiscapital.dto.TransferRequestDTO;
import com.aegiscapital.entity.Transaction;

import java.util.List;

public interface TransactionService
{
    void transferFunds(TransferRequestDTO request);

    List<TransactionResponseDTO> getTransactions(String accountNumber);

}
