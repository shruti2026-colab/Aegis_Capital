package com.aegiscapital.controller;



import com.aegiscapital.dto.*;
import com.aegiscapital.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // To register a new user
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request) {

        return authService.register(request);
    }


    // user can login using email and password
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    // user can login using accountId and password
    @PostMapping("/loginUsingAccountId")
    public String loginUsingAccountId(@RequestBody LoginAccountIdDTO request){

        return authService.login(request);
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody ResetPasswordDTO request){
        return authService.resetPassword(request);
    }

}