package com.aegiscapital.service;

import com.aegiscapital.IdGenerator.IdGeneratorImpl;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.Transaction;
import com.aegiscapital.exception.AccountNotFoundException;
import com.aegiscapital.exception.InsufficientBalanceException;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService
{
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final IdGeneratorImpl idGenerator;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, IdGeneratorImpl idGenerator) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public void deposit(String accountNumber, BigDecimal amount)
    {
        // throws new custom made exception if account not found
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        // deposit transaction details and time are stored directly
        Transaction transaction = Transaction.builder()
                .fromAccount(account) // sender accountid is stored
                .toAccount(null) //since deposit done for self account, toAccount will be null
                .amount(amount)
                .status("Deposit SUCCESS")
                .timestamp(LocalDateTime.now())
                .transactionId(idGenerator.generateTransactionId())
                .fromAccountNumber(accountNumber)
                .build();

        transactionRepository.save(transaction);
    }

    @Override
    public void withdraw(String accountNumber, BigDecimal amount)
    {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if(account.getBalance().compareTo(amount) < 0)
        {
            // throws new custom made exception if there no sufficient balance in account
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        accountRepository.save(account);

        // withdraw transaction details and time are stored directly
        Transaction transaction = Transaction.builder()
                .fromAccount(account) // withdrawer accountid is stored
                .toAccount(null) //since withdraw is done from self account toAccoun will be null
                .amount(amount)
                .status("Withdraw SUCCESS")
                .timestamp(LocalDateTime.now())
                .transactionId(idGenerator.generateTransactionId())
                .fromAccountNumber(accountNumber)
                .build();

        transactionRepository.save(transaction);
    }

    @Override
    public BigDecimal getBalance(String accountNumber)
    {
       // throws new custom made exception if account not found
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        return account.getBalance();
    }
}
