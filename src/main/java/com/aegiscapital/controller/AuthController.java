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

    // To register a new user
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    //only existing users can open account
    @PostMapping("/openAccount")
    public String openAccount(@RequestBody RegisterAccountDTO request){
        return authService.openAccount(request);
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
}