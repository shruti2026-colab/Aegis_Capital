package com.aegiscapital.controller;



import com.aegiscapital.dto.LoginAccountIdDTO;
import com.aegiscapital.dto.LoginRequestDTO;
import com.aegiscapital.dto.RegisterRequestDTO;
import com.aegiscapital.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //register api with name, email and password as input
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    //login api with email and password as input
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    //login api using account id and password
    @PostMapping("/loginUsingAccountId")
    public String loginUsingAccountId(@RequestBody LoginAccountIdDTO request){

        return authService.login(request);
    }
}