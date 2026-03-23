package com.aegiscapital.service;

import com.aegiscapital.IdGenerator.IdGeneratorImpl;
import com.aegiscapital.dto.LoginAccountIdDTO;
import com.aegiscapital.dto.LoginRequestDTO;
import com.aegiscapital.dto.RegisterAccountDTO;
import com.aegiscapital.dto.RegisterRequestDTO;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.User;
import com.aegiscapital.exception.*;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.UserRepository;
import com.aegiscapital.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final IdGeneratorImpl idGenerator;
    private final JwtUtil jwtUtil;
    //  REGISTER USER
    @Override
    public String register(RegisterRequestDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new SimilarEmailException("Email already exists!\nPlease enter another email address");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserId(idGenerator.generateUserId(request.getName()));

        userRepository.save(user);

        return "User registered successfully!";
    }

    // LOGIN USING EMAIL
    @Override
    public String login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            throw new InvalidUserException("User does not exist!\nUser should be registered first or enter an existing user email");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("Invalid password!");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    // LOGIN USING ACCOUNT ID
    @Override
    public String login(LoginAccountIdDTO request) {

        Account acc = accountRepository.findByAccountNumber(request.getAccountNumber())
        .orElse(null);

        if (acc == null) {
            throw new InvalidAccountNumberException("Invalid account number!\nplease Enter valid account number!!!");
        }

        User user = acc.getUser();

        if (user == null) {
            throw new InvalidUserException("User does not exist!\nUser should be registered first or enter an existing user number");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IncorrectPasswordException("Invalid password!");
        }

        return "Login successful!";
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
}