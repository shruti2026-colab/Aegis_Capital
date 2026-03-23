package com.aegiscapital.service;

import com.aegiscapital.IdGenerator.IdGeneratorImpl;
import com.aegiscapital.dto.DepositRequestDTO;
import com.aegiscapital.dto.WithdrawRequestDTO;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.Transaction;
import com.aegiscapital.exception.AccountNotFoundException;
import com.aegiscapital.exception.IncorrectPINException;
import com.aegiscapital.exception.InsufficientBalanceException;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

@Service
public class AccountServiceImpl implements AccountService
{
    Scanner sc = new Scanner(System.in);
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final IdGeneratorImpl idGenerator;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, IdGeneratorImpl idGenerator, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.idGenerator = idGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void deposit(DepositRequestDTO request)
    {
        String accountNumber = request.getAccountNumber();
        BigDecimal amount = request.getAmount();
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
    public void withdraw(WithdrawRequestDTO request)
    {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if(account.getBalance().compareTo(request.getAmount()) < 0)
        {
            // throws new custom made exception if there no sufficient balance in account
            throw new InsufficientBalanceException("Insufficient balance");
        }
        System.out.println("Enter the Pin: ");
        String pin = sc.next();
        if(!passwordEncoder.matches(pin, account.getPin())){
            throw new IncorrectPINException("Enter the right pin");
        }
        account.setBalance(account.getBalance().subtract(request.getAmount()));

        accountRepository.save(account);

        // withdraw transaction details and time are stored directly
        Transaction transaction = Transaction.builder()
                .fromAccount(account) // withdrawer accountid is stored
                .toAccount(null) //since withdraw is done from self account toAccoun will be null
                .amount(request.getAmount())
                .status("Withdraw SUCCESS")
                .timestamp(LocalDateTime.now())
                .transactionId(idGenerator.generateTransactionId())
                .fromAccountNumber(request.getAccountNumber())
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
