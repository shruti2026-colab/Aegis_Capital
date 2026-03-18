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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final IdGeneratorImpl idGenerator;

    @Override
    public String register(RegisterRequestDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists!";
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setMobileNumber(request.getMobileNumber());

        userRepository.save(user);

        User savedUser = user;
        savedUser.setUserId(idGenerator.generateUserId(user.getName(), user.getId()));
        userRepository.save(savedUser);

        return "User registered successfully!";
    }

    @Override
    public String openAccount(RegisterAccountDTO request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Amount cannot be negative");
        }

        if (userRepository.findById(request.getUser().getId()).isPresent()) {

            Account account = new Account();
            account.setUser(request.getUser());
            account.setBalance(request.getAmount());
            accountRepository.save(account);

            Account savedAccount = account;
            savedAccount.setAccountNumber(idGenerator.generateAccountId(account.getId()));
            accountRepository.save(savedAccount);
            return "Account created successfully!!";

        } else {
            return "User doesnot exist";
        }
    }



    @Override
    public String login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return "User does not exist!";
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return "Invalid password!";
        }

        return "Login successful!";
    }

    @Override
    public String login(LoginAccountIdDTO request) {
        Account acc = accountRepository.findById(request.getAccountId()).orElse(null);

        if(acc == null){
            return "Invalid account number!";
        }
        User user = userRepository.findById(acc.getUser().getId())
                .orElse(null);
        if(user == null) {
            return "User doesnot Exist";
        }
            if (!user.getPassword().equals(request.getPassword())) {
                return "Invalid password!!";
            }
        return "Login successfull!";
    }
}