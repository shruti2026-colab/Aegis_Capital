package com.aegiscapital.service;

import com.aegiscapital.IdGenerator.IdGeneratorImpl;
import com.aegiscapital.dto.LoginRequestDTO;
import com.aegiscapital.dto.RegisterRequestDTO;
import com.aegiscapital.entity.User;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.UserRepository;
import com.aegiscapital.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IdGeneratorImpl idGenerator;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testRegister_success() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setEmail("test@gmail.com");
        request.setPassword("1234");
        request.setName("Test");
        request.setMobileNumber("9999999999");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode("1234")).thenReturn("encoded123");
        when(idGenerator.generateUserId("Test")).thenReturn("USR123");

        String result = authService.register(request);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testLogin_success() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("test@gmail.com");
        request.setPassword("1234");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("encoded123");
        user.setRole("ROLE_USER");
        user.setToken(null);
        user.setTokenExpiry(null);

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encoded123")).thenReturn(true);
        when(jwtUtil.generateToken(any(), any())).thenReturn("token123");

        String token = authService.login(request);

        verify(userRepository, times(1)).save(user);
    }


}