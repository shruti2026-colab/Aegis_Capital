package com.aegiscapital.service;

import com.aegiscapital.IdGenerator.IdGeneratorImpl;
import com.aegiscapital.dto.LoginAccountIdDTO;
import com.aegiscapital.dto.LoginRequestDTO;
import com.aegiscapital.dto.RegisterAccountDTO;
import com.aegiscapital.dto.RegisterRequestDTO;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.User;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final IdGeneratorImpl idGenerator;

    //  REGISTER USER
    @Override
    public String register(RegisterRequestDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists!";
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
       user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        User savedUser = userRepository.save(user);
        savedUser.setUserId(
                idGenerator.generateUserId(request.getName(), savedUser.getId())
        );
        userRepository.save(savedUser);

        return "User registered successfully!";
    }

    // LOGIN USING EMAIL
    @Override
    public String login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return "User does not exist!";
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Invalid password!";
        }

        return "Login successful!";
    }

    // LOGIN USING ACCOUNT ID
    @Override
    public String login(LoginAccountIdDTO request) {

        Account acc = accountRepository.findById(request.getAccountId())
                .orElse(null);

        if (acc == null) {
            return "Invalid account number!";
        }

        User user = acc.getUser();

        if (user == null) {
            return "User does not exist!";
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Invalid password!";
        }

        return "Login successful!";
    }
    @Override
    public String openAccount(RegisterAccountDTO request) {

        return "Account opened successfully!";
    }
}