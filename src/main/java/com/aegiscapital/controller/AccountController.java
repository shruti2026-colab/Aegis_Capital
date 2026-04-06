package com.aegiscapital.controller;

import com.aegiscapital.dto.*;
import com.aegiscapital.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    //deposit api with account id and amount as input
    @PostMapping("/deposit")
    public ResponseEntity<SuccessResponseDTO<Void>> deposit(@RequestBody DepositRequestDTO request) {

        accountService.deposit(request);
        SuccessResponseDTO<Void> response = new SuccessResponseDTO<>(
                200,
                "Deposit Successful",
                null
        );
        return ResponseEntity.ok(response);
    }

    //withdraw api with account id, password and amount as input
    @PostMapping("/withdraw")
    public ResponseEntity<SuccessResponseDTO<Void>> withdraw(@RequestBody WithdrawRequestDTO request) {
        accountService.withdraw(request);
        SuccessResponseDTO<Void> response = new SuccessResponseDTO<>(
                200,
                "Withdraw Successful",
                null
        );
        return ResponseEntity.ok(response);
    }

    //get balance api with account id as input
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<SuccessResponseDTO<BigDecimal>> getBalance(@PathVariable String accountNumber) {
        BigDecimal balance = accountService.getBalance(accountNumber);

        SuccessResponseDTO<BigDecimal> response = new SuccessResponseDTO<>(
                200,
                "Balance fetched successfully",
                balance
        );
        return ResponseEntity.ok(response);
    }

    //only existing users can open account
    @PostMapping("/openAccount")
    public ResponseEntity<SuccessResponseDTO<Void>> openAccount(@RequestBody RegisterAccountDTO request){
        accountService.openAccount(request);
        SuccessResponseDTO<Void> response = new SuccessResponseDTO<>(
                200,
                "account opened Successful",
                null
        );
        return ResponseEntity.ok(response);
    }
    
    // get complete account details
    @GetMapping("/{accountNumber}/getdetails")
    public ResponseEntity<AccountResponseDTO> getDetails(@PathVariable String accountNumber){
        return  ResponseEntity.ok(accountService.getDetails(accountNumber));
    }

    // get user details and the accounts he/she hold
    @GetMapping("/{userId}/details")
    public ResponseEntity<List<AccountResponseDTO>> details(@PathVariable String userId){

        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }
}
