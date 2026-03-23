package com.aegiscapital.service;

import com.aegiscapital.IdGenerator.IdGeneratorImpl;
import com.aegiscapital.dto.TransactionResponseDTO;
import com.aegiscapital.dto.TransferRequestDTO;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.Transaction;
import com.aegiscapital.exception.AccountNotFoundException;
import com.aegiscapital.exception.InsufficientBalanceException;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.TransactionRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService
{
    Scanner sc = new Scanner(System.in);
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final IdGeneratorImpl idGenerator;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void transferFunds(TransferRequestDTO request)
    {
        // throws new custom made exception if account not found for both sender and receiver
        Account sender = accountRepository.findByAccountNumber(request.getFromAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found"));
        Account receiver = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"));

        BigDecimal amount = request.getAmount();

        if(sender.getBalance().compareTo(amount) < 0)
        {
            // throws new custom made exception if balance is insufficient
            throw new InsufficientBalanceException("Insufficient balance");
        }
        System.out.println("Enter the Pin: ");
        String pin = sc.next();
        if(!passwordEncoder.matches(pin, sender.getPin())){
            throw new RuntimeException("Enter the right pin");
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
                .transactionId(idGenerator.generateTransactionId())
                .fromAccountNumber(sender.getAccountNumber())
                .toAccountNumber(receiver.getAccountNumber())
                .build();

        transactionRepository.save(transaction);

    }


    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getTransactions(String accountNumber) {
        return transactionRepository
                .findByFromAccountNumberOrToAccountNumberOrderByTimestampDesc(accountNumber, accountNumber)
                .stream()
                .map(t -> toDTO(t, accountNumber))
                .collect(Collectors.toList());
    }
    private TransactionResponseDTO toDTO(Transaction t, String viewerAccountNumber) {
        String type = (t.getFromAccount() != null &&
                t.getFromAccount().getAccountNumber().equals(viewerAccountNumber))
                ? "DEBIT" : "CREDIT";

        return TransactionResponseDTO.builder()
                .transactionId(t.getId())
                .fromAccountNumber(t.getFromAccount() != null ? t.getFromAccount().getAccountNumber() : null)
                .toAccountNumber(t.getToAccount() != null ? t.getToAccount().getAccountNumber() : null)
                .amount(t.getAmount())
                .status(t.getStatus())
                .type(type)
                .timestamp(t.getTimestamp())
                .build();
    }
}




