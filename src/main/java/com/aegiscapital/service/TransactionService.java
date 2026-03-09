package com.aegiscapital.service;

import com.aegiscapital.dto.TransferRequestDTO;

public interface TransactionService
{
    void transferFunds(TransferRequestDTO request);
}
