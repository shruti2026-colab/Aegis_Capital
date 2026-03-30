package com.aegiscapital.service;


import com.aegiscapital.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthService {
    String register(RegisterRequestDTO request);
    String login(LoginRequestDTO request);
    String login(LoginAccountIdDTO request);
    String logout(String userId);
    String resetPassword(ResetPasswordDTO request);
}
