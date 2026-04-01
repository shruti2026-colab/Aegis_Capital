package com.aegiscapital.service;

import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.User;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.TransactionRepository;
import com.aegiscapital.respository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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