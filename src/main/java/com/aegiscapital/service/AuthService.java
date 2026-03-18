package com.aegiscapital.service;


import com.aegiscapital.dto.LoginAccountIdDTO;
import com.aegiscapital.dto.RegisterAccountDTO;
import com.aegiscapital.dto.RegisterRequestDTO;
import com.aegiscapital.dto.LoginRequestDTO;

public interface AuthService {
    String register(RegisterRequestDTO request);
    String login(LoginRequestDTO request);
    String login(LoginAccountIdDTO request);

    String openAccount(RegisterAccountDTO request);
}
