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
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService
{
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final IdGeneratorImpl idGenerator;

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

        Transaction savedTransaction = transaction;
        savedTransaction.setTransactionId(idGenerator.generateTransactionId(transaction.getId()));
        transactionRepository.save(savedTransaction);
    }



    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getTransactions(Long accountId) {
        return transactionRepository
                .findByFromAccount_IdOrToAccount_IdOrderByTimestampDesc(accountId, accountId)
                .stream()
                .map(t -> toDTO(t, accountId))
                .collect(Collectors.toList());
    }
    private TransactionResponseDTO toDTO(Transaction t, Long viewerAccountId) {
        String type = (t.getFromAccount() != null &&
                t.getFromAccount().getId().equals(viewerAccountId))
                ? "DEBIT" : "CREDIT";

        return TransactionResponseDTO.builder()
                .transactionId(t.getId())
                .fromAccountId(t.getFromAccount() != null ? t.getFromAccount().getId() : null)
                .toAccountId(t.getToAccount() != null ? t.getToAccount().getId() : null)
                .amount(t.getAmount())
                .status(t.getStatus())
                .type(type)
                .timestamp(t.getTimestamp())
                .build();
    }
}




