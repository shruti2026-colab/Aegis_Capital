package com.aegiscapital.service;

import com.aegiscapital.entity.Account;
import com.aegiscapital.exception.AccountNotFoundException;
import com.aegiscapital.exception.InsufficientBalanceException;
import com.aegiscapital.respository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService
{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void deposit(Long accountId, BigDecimal amount)
    {
        // throws new custom made exception if account not found
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Override
    public void withdraw(Long accountId, BigDecimal amount)
    {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if(account.getBalance().compareTo(amount) < 0)
        {
            // throws new custom made exception if there no sufficient balance in account
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));

        accountRepository.save(account);
    }

    @Override
    public BigDecimal getBalance(Long accountId)
    {
       // throws new custom made exception if account not found
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        return account.getBalance();
    }
}
