package com.aegiscapital.service;



import com.aegiscapital.dto.AccountAdminDTO;
import com.aegiscapital.dto.TransactionAdminDTO;
import com.aegiscapital.dto.UserResponseDTO;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.User;
import com.aegiscapital.exception.AccountNotFoundException;
import com.aegiscapital.repository.AccountRepository;
import com.aegiscapital.repository.TransactionRepository;
import com.aegiscapital.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;



    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> UserResponseDTO.builder()
                        .id(user.getId())
                        .userId(user.getUserId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .mobileNumber(user.getMobileNumber())
                        .role(user.getRole())
                        .build())
                .toList();
    }

    @Override
    public List<AccountAdminDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> new AccountAdminDTO(
                        account.getAccountNumber(),
                        account.getBalance(),
                        account.getUser().getEmail()
                ))
                .toList();
    }



    @Override
    public List<TransactionAdminDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(tx -> new TransactionAdminDTO(
                        tx.getTransactionId(),
                        tx.getFromAccountNumber(),
                        tx.getToAccountNumber(),
                        tx.getAmount(),
                        tx.getStatus(),
                        tx.getTimestamp()
                ))
                .toList();
    }


    @Override
    @Transactional
    public void deleteAccount(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        account.setActive(false);      // soft delete
        accountRepository.save(account); // persist change
    }

        @Override
    public void promoteToAdmin(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole("ROLE_ADMIN");
        userRepository.save(user);
    }
}