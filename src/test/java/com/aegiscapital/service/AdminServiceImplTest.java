package com.aegiscapital.service;

import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.Transaction;
import com.aegiscapital.entity.User;
import com.aegiscapital.repository.AccountRepository;
import com.aegiscapital.repository.TransactionRepository;
import com.aegiscapital.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AdminServiceImpl adminService;
    //get all users
    @Test
    void testGetAllUsers() {
        User user = new User();
        user.setId(1L);
        user.setUserId("USR1");
        user.setName("Richa");
        user.setEmail("richa@gmail.com");
        user.setMobileNumber("9999999999");
        user.setRole("ROLE_USER");

        when(userRepository.findAll()).thenReturn(List.of(user));

        var result = adminService.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    // getAllAccounts()
    @Test
    void testGetAllAccounts() {
        User user = new User();
        user.setEmail("test@gmail.com");

        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setBalance(BigDecimal.valueOf(1000));
        account.setUser(user);

        when(accountRepository.findAll()).thenReturn(List.of(account));

        var result = adminService.getAllAccounts();

        assertEquals(1, result.size());
        verify(accountRepository, times(1)).findAll();
    }

    // getAllTransactions()
    @Test
    void testGetAllTransactions() {
        Transaction tx = new Transaction();
        tx.setTransactionId("TXN1");
        tx.setFromAccountNumber("ACC1");
        tx.setToAccountNumber("ACC2");
        tx.setAmount(BigDecimal.valueOf(500));
        tx.setStatus("SUCCESS");
        tx.setTimestamp(LocalDateTime.now());

        when(transactionRepository.findAll()).thenReturn(List.of(tx));

        var result = adminService.getAllTransactions();

        assertEquals(1, result.size());
        verify(transactionRepository, times(1)).findAll();
    }
    //delete account
    @Test
    void testDeleteAccount_success() {
        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setActive(true);

        when(accountRepository.findByAccountNumber("ACC123"))
                .thenReturn(Optional.of(account));

        adminService.deleteAccount("ACC123");

        verify(accountRepository, times(1)).save(account);
    }
//promote to admin
    @Test
    void testPromoteToAdmin_success() {
        User user = new User();
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        adminService.promoteToAdmin("test@gmail.com");

        verify(userRepository, times(1)).save(user);
    }
}