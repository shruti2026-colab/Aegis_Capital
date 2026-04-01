package com.aegiscapital.service;

import com.aegiscapital.IdGenerator.IdGeneratorImpl;
import com.aegiscapital.dto.AccountResponseDTO;
import com.aegiscapital.dto.DepositRequestDTO;
import com.aegiscapital.dto.RegisterAccountDTO;
import com.aegiscapital.dto.WithdrawRequestDTO;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.Transaction;
import com.aegiscapital.entity.User;
import com.aegiscapital.exception.*;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.TransactionRepository;
import com.aegiscapital.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService
{
    Scanner sc = new Scanner(System.in);
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final IdGeneratorImpl idGenerator;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, UserRepository userRepository, IdGeneratorImpl idGenerator, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void deposit(DepositRequestDTO request)
    {
        String accountNumber = request.getAccountNumber();
        BigDecimal amount = request.getAmount();
        // throws new custom made exception if account not found
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        validateAccountOwnership(account);
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
    @Transactional
    public void withdraw(WithdrawRequestDTO request)
    {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        validateAccountOwnership(account);
        if(account.getBalance().compareTo(request.getAmount()) < 0)
        {
            // throws new custom made exception if there no sufficient balance in account
            throw new InsufficientBalanceException("Insufficient balance");
        }


        if(!passwordEncoder.matches(request.getPin(), account.getPin())){
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
        validateAccountOwnership(account);
        return account.getBalance();
    }

    @Override
    public String openAccount(RegisterAccountDTO request) {

        if (request.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeNumberException("Amount cannot be negative");
        }

        User user = userRepository.findByUserId(request.getUserId());
        if(user != null) {
            Account account = new Account();
            account.setUser(user);
            account.setBalance(request.getAmount());
            account.setAccountNumber(idGenerator.generateAccountNumber());
            account.setPin(passwordEncoder.encode(request.getPin()));
            accountRepository.save(account);
            return "Account created successfully!!";
        }
        else{
            throw new InvalidUserException("User does not exist!\nUser should be registered first to open an account or enter an existing user number");
        }


    }

    private void validateAccountOwnership(Account account) {

        String loggedInEmail = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (account.getUser() == null ||
                !account.getUser().getEmail().equals(loggedInEmail)) {

            throw new UnauthorizedAccessException("You are not authorized to access this account");
        }
    }


    @Override
    public AccountResponseDTO getDetails(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        AccountResponseDTO details = new AccountResponseDTO(account.getAccountNumber(),account.getBalance(), account.getUser().getUserId(),account.isActive()?"ACTIVE":"INACTIVE");
        return details;
    }

    @Override
    public List<AccountResponseDTO> getAccountsByUserId(String userId) {
        User user = userRepository.findByUserId(userId);
        if(user == null){
            throw new InvalidUserException("User not found");
        }

        List<Account> accounts = accountRepository.findByUser_UserId(userId);

        List<AccountResponseDTO> details =  accounts.stream()
                .map(acc -> new AccountResponseDTO(
                        acc.getAccountNumber(),
                        acc.getBalance(),
                        acc.getUser().getUserId(),
                        acc.isActive() ? "ACTIVE":"INACTIVE"
                ))
                .collect(Collectors.toList());

        return details;
    }

}
