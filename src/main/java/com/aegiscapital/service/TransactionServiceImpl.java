package com.aegiscapital.service;

import com.aegiscapital.dto.TransferRequestDTO;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.Transaction;
import com.aegiscapital.exception.AccountNotFoundException;
import com.aegiscapital.exception.InsufficientBalanceException;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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
        // throws new custom made exception if account not found for both sender and receiver
        Account sender = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found"));
        Account receiver = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"));

        BigDecimal amount = request.getAmount();

        if(sender.getBalance().compareTo(amount) < 0)
        {
            // throws new custom made exception if balance is insufficient
            throw new InsufficientBalanceException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        // funds transfer details and time are stored directly
        Transaction transaction = Transaction.builder()
                .fromAccount(sender) // sender accountid is stored
                .toAccount(receiver) //receiver accountid is stored
                .amount(amount)
                .status("Funds Transferring SUCCESS")
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Long accountId)
    {
        return transactionRepository
                .findByFromAccountIdOrToAccountId(accountId, accountId);
    }
}
