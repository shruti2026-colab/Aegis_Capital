package com.aegiscapital.transaction;

import com.aegiscapital.dto.TransactionResponseDTO;
import com.aegiscapital.entity.Transaction;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.User;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.TransactionRepository;
import com.aegiscapital.service.TransactionServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Enables Mockito support
public class TransactionServiceTest {

    // Mocking dependencies (no real DB calls)
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    // Inject mocks into service
    @InjectMocks
    private TransactionServiceImpl transactionService;

    // ================= SECURITY CONTEXT =================

    @BeforeEach
    void setupSecurityContext() {
        // Mocking logged-in user
        Authentication authentication = mock(Authentication.class);

        // lenient avoids unnecessary stubbing exception
        lenient().when(authentication.getName()).thenReturn("test@gmail.com");

        // Setting authentication in Spring Security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // ================= SAVE TRANSACTION =================

    @Test
    void testSaveTransaction() {

        // Creating dummy user
        User user = new User();
        user.setEmail("test@gmail.com");

        // Creating account linked to user
        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setUser(user);

        // Creating transaction object
        Transaction txn = new Transaction();
        txn.setTransactionId("TXN123");
        txn.setAmount(BigDecimal.valueOf(1000));
        txn.setStatus("DEPOSIT");
        txn.setFromAccount(account);

        // Mock repository save behavior
        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(txn);

        // Calling repository directly (NOTE: not testing service here)
        Transaction result = transactionRepository.save(txn);

        // Assertions
        assertNotNull(result);
        assertEquals("TXN123", result.getTransactionId());

        // Verify save method called once
        verify(transactionRepository, times(1)).save(txn);
    }

    // ================= GET ALL TRANSACTIONS =================

    @Test
    void testGetAllTransactionsByAccount() {

        // Mock logged-in user
        User user = new User();
        user.setEmail("test@gmail.com");

        // Mock account
        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setUser(user);

        // Mock transaction
        Transaction txn = new Transaction();
        txn.setTransactionId("TXN123");
        txn.setAmount(BigDecimal.valueOf(1000));
        txn.setStatus("DEPOSIT");
        txn.setFromAccount(account);

        // Mock account lookup (validation step in service)
        when(accountRepository.findByAccountNumber(anyString()))
                .thenReturn(Optional.of(account));

        // Mock transaction fetch
        when(transactionRepository
                .findByFromAccountNumberOrToAccountNumberOrderByTimestampDesc(anyString(), anyString()))
                .thenReturn(List.of(txn));

        // Call service method
        List<TransactionResponseDTO> result =
                transactionService.getTransactions("ACC123");

        // Assertions
        assertEquals(1, result.size());
        assertEquals("TXN123", result.getFirst().getTransactionId());
    }

    // ================= EMPTY TRANSACTION =================

    @Test
    void testGetTransactions_Empty() {

        // Mock user
        User user = new User();
        user.setEmail("test@gmail.com");

        // Mock account
        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setUser(user);

        // Mock account exists
        when(accountRepository.findByAccountNumber("ACC123"))
                .thenReturn(Optional.of(account));

        // No transactions mocked → returns empty list by default

        // Call service
        List<TransactionResponseDTO> result =
                transactionService.getTransactions("ACC123");

        // Assertion: should be empty
        assertTrue(result.isEmpty());
    }
}