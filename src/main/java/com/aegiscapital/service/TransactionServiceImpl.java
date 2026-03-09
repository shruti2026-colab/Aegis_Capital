package com.aegiscapital.service;

import com.aegiscapital.dto.TransferRequestDTO;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.Transaction;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService
{
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void transferFunds(TransferRequestDTO request)
    {
        Account sender = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));
        Account receiver = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        BigDecimal amount = request.getAmount();

        if(sender.getBalance().compareTo(amount) < 0)
        {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction transaction = Transaction.builder()
                .fromAccount(sender)
                .toAccount(receiver)
                .amount(amount)
                .status("SUCCESS")
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
