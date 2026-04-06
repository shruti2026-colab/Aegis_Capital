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
    public ResponseEntity<SuccessResponseDTO> register(@RequestBody RegisterRequestDTO request) {

         authService.register(request);

        SuccessResponseDTO<Void> response = new SuccessResponseDTO<>(
                200,
                "User Registered Successfully",
                null
        );
        return ResponseEntity.ok(response);
    }


    // user can login using email and password
    @PostMapping("/login")
    public ResponseEntity<SuccessResponseDTO<String>> login(@RequestBody LoginRequestDTO request) {
        String token = authService.login(request);

        SuccessResponseDTO<String> response = new SuccessResponseDTO<>(
                200,
                "Login Successful",
                token

        );
        return ResponseEntity.ok(response);
    }

    // user can login using accountId and password
    @PostMapping("/loginUsingAccountId")
    public ResponseEntity<SuccessResponseDTO<String>> loginUsingAccountId(@RequestBody LoginAccountIdDTO request){

        String token = authService.login(request);

        SuccessResponseDTO<String> response = new SuccessResponseDTO<>(
                200,
                "Login Successful",
                token
        );
        return ResponseEntity.ok(response);
    }

    //user can logout
    @GetMapping("/{userId}/logout")
    public ResponseEntity<SuccessResponseDTO> logout(@PathVariable String userId)
    {
         authService.logout(userId);

        SuccessResponseDTO<Void> response = new SuccessResponseDTO<>(
                200,
                "Logout Successful",
                null
        );
        return ResponseEntity.ok(response);
    }


    // user can reset password
    @PostMapping("/resetPassword")
    public ResponseEntity<SuccessResponseDTO<Void>> resetPassword(@RequestBody ResetPasswordDTO request){
         authService.resetPassword(request);

        SuccessResponseDTO<Void> response = new SuccessResponseDTO<>(
                200,
                "Password reset Successful",
                null
        );
        return ResponseEntity.ok(response);
    }

}