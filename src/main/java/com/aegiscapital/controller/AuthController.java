package com.aegiscapital.controller;



import com.aegiscapital.dto.LoginAccountIdDTO;
import com.aegiscapital.dto.LoginRequestDTO;
import com.aegiscapital.dto.RegisterAccountDTO;
import com.aegiscapital.dto.RegisterRequestDTO;
import com.aegiscapital.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/openAccount")
    public String openAccount(@RequestBody RegisterAccountDTO request){
        return authService.openAccount(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/loginUsingAccountId")
    public String loginUsingAccountId(@RequestBody LoginAccountIdDTO request){

        return authService.login(request);
    }
}