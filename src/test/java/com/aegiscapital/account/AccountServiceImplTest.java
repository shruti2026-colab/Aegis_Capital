package com.aegiscapital.account;

import com.aegiscapital.IdGenerator.IdGeneratorImpl;
import com.aegiscapital.dto.*;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.User;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.TransactionRepository;
import com.aegiscapital.respository.UserRepository;
import com.aegiscapital.service.AccountServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Enable Mockito support for this test class
@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    // Mocking repository dependencies
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    // Mocking utility classes
    @Mock
    private IdGeneratorImpl idGenerator;

    @Mock
    private PasswordEncoder passwordEncoder;

    // Inject mocks into the service being tested
    @InjectMocks
    private AccountServiceImpl accountService;

    // Setting up a fake logged-in user before each test
    @BeforeEach
    void setupSecurityContext() {
        Authentication authentication = mock(Authentication.class);
        lenient().when(authentication.getName()).thenReturn("test@gmail.com");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // ================= DEPOSIT =================

    @Test
    void testDeposit() {
        // Creating request DTO
        DepositRequestDTO request = new DepositRequestDTO();
        request.setAccountNumber("ACC123");
        request.setAmount(BigDecimal.valueOf(1000));

        // Creating user and account
        User user = new User();
        user.setEmail("test@gmail.com");

        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setBalance(BigDecimal.valueOf(500));
        account.setUser(user);

        // Mocking repository response
        when(accountRepository.findByAccountNumber("ACC123"))
                .thenReturn(Optional.of(account));

        // Mocking transaction ID generation
        when(idGenerator.generateTransactionId()).thenReturn("TXN123");

        // Calling deposit method
        accountService.deposit(request);

        // Verifying account and transaction are saved
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any());
    }

    // ================= WITHDRAW =================

    @Test
    @Timeout(1) // Ensures test completes within 1 second
    void testWithdrawSuccess() {
        // Creating withdraw request
        WithdrawRequestDTO request = new WithdrawRequestDTO();
        request.setAccountNumber("ACC123");
        request.setAmount(BigDecimal.valueOf(1000));
        request.setPin("1234");

        // Creating user and account
        User user = new User();
        user.setEmail("test@gmail.com");

        Account account = new Account();
        account.setAccountNumber("ACC123");
        account.setBalance(BigDecimal.valueOf(5000));
        account.setUser(user);
        account.setPin("1234");

        // Mocking repository response
        when(accountRepository.findByAccountNumber("ACC123"))
                .thenReturn(Optional.of(account));

        // Mocking transaction ID
        when(idGenerator.generateTransactionId()).thenReturn("TXN123");

        // Mocking PIN validation
        when(passwordEncoder.matches("1234","1234"))
                .thenReturn(true);

        // Calling withdraw method
        accountService.withdraw(request);

        // Verifying updates
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any());
    }


    // ================= GET BALANCE =================

    @Test
    void testGetBalance() {
        // Creating user and account
        User user = new User();
        user.setEmail("test@gmail.com");

        Account account = new Account();
        account.setBalance(new BigDecimal("2000"));
        account.setUser(user);

        // Mocking repository response
        when(accountRepository.findByAccountNumber(anyString()))
                .thenReturn(Optional.of(account));

        // Calling method
        BigDecimal balance = accountService.getBalance("AGS-123");

        // Verifying result
        assertEquals(new BigDecimal("2000"), balance);
    }

    // ================= OPEN ACCOUNT =================

    @Test
    void testOpenAccount() {

        // Creating user
        User user = new User();
        user.setEmail("test@gmail.com");

        // Creating account request
        RegisterAccountDTO request = new RegisterAccountDTO();
        request.setUserId("USR123");
        request.setAmount(new BigDecimal("5000"));
        request.setPin("1234");

        // Mocking user lookup
        when(userRepository.findByUserId("USR123"))
                .thenReturn(user);

        // Mocking PIN encoding
        when(passwordEncoder.encode("1234"))
                .thenReturn("encodedPin");

        // Mocking save behavior
        when(accountRepository.save(any(Account.class)))
                .thenAnswer(i -> i.getArgument(0));

        // Calling method
        String result = accountService.openAccount(request);

        // Verifying result
        assertNotNull(result);
        assertEquals("Account created successfully!!", result);
    }

    // ================= GET ACCOUNT DETAILS =================

    @Test
    void testGetAccountByNumber() {
        // Creating user and account
        User user = new User();
        user.setEmail("test@gmail.com");

        Account account = new Account();
        account.setAccountNumber("AGS-123");
        account.setBalance(new BigDecimal("1000"));
        account.setPin("1234");
        account.setUser(user);

        // Mocking repository response
        when(accountRepository.findByAccountNumber("AGS-123"))
                .thenReturn(Optional.of(account));

        // Calling method
        AccountResponseDTO result = accountService.getDetails("AGS-123");

        // Verifying account number
        assertEquals("AGS-123", result.getAccountNumber());
    }

    // ================= GET ALL ACCOUNTS =================

    @Test
    void testGetAccountsByUserId() {
        // Creating user
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setUserId("USR123");

        // Creating account
        Account acc = new Account();
        acc.setAccountNumber("AGS-111");
        acc.setBalance(new BigDecimal("1000"));
        acc.setUser(user);

        // Mocking repository calls
        when(userRepository.findByUserId("USR123"))
                .thenReturn(user);

        when(accountRepository.findByUser_UserId("USR123"))
                .thenReturn(List.of(acc));

        // Calling method
        List<AccountResponseDTO> result =
                accountService.getAccountsByUserId("USR123");

        // Verifying results
        assertEquals(1, result.size());
        assertEquals("AGS-111", result.get(0).getAccountNumber());
    }
}