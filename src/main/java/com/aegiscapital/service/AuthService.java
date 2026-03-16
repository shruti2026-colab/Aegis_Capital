package com.aegiscapital.service;


import com.aegiscapital.dto.LoginAccountId;
import com.aegiscapital.dto.RegisterRequestDTO;
import com.aegiscapital.dto.LoginRequestDTO;

public interface AuthService {
    String register(RegisterRequestDTO request);
    String login(LoginRequestDTO request);
    String login(LoginAccountId request);
}
