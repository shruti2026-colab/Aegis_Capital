package com.aegiscapital.service;

import com.aegiscapital.IdGenerator.IdGeneratorImpl;
import com.aegiscapital.dto.*;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.User;
import com.aegiscapital.exception.*;
import com.aegiscapital.respository.AccountRepository;
import com.aegiscapital.respository.UserRepository;
import com.aegiscapital.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final IdGeneratorImpl idGenerator;
    private final JwtUtil jwtUtil;

    Scanner sc = new Scanner(System.in);
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
        user.setRole("ROLE_USER");
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

        return jwtUtil.generateToken(user.getEmail(),user.getRole());
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

        return jwtUtil.generateToken(user.getEmail(),user.getRole());
    }


    @Override
    public String resetPassword(ResetPasswordDTO request) {
        User user = userRepository.findByUserId(request.getUserId());
        if(user == null){
            throw new InvalidUserException("User does not exist!\nUser should be registered first to open an account or enter an existing user number");
        }
        if(user.getEmail().equals(request.getEmail()) && user.getMobileNumber().equals(request.getMobileNumber())) {
            Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                    .orElseThrow(() -> new InvalidAccountNumberException("Invalid account number!\nnplease Enter valid account number!!!"));
            System.out.println("Enter new password");
            String password =  sc.nextLine();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
        else{
            throw new InvalidUserException("Enter valid Email/Mobile number ");
        }
        return "Password reset successfully";
    }
}