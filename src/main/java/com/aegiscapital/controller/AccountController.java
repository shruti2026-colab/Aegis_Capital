package com.aegiscapital.controller;

import com.aegiscapital.dto.AccountResponseDTO;
import com.aegiscapital.dto.DepositRequestDTO;
import com.aegiscapital.dto.RegisterAccountDTO;
import com.aegiscapital.dto.WithdrawRequestDTO;
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
    public String deposit(@RequestBody DepositRequestDTO request) {

        accountService.deposit(request);
        return "Deposit successful";
    }

    //withdraw api with account id, password and amount as input
    @PostMapping("/withdraw")
    public String withdraw(@RequestBody WithdrawRequestDTO request) {

        accountService.withdraw(request);
        return "Withdraw successful";
    }

    //get balance api with account id as input
    @GetMapping("/{accountNumber}/balance")
    public BigDecimal getBalance(@PathVariable String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    //only existing users can open account
    @PostMapping("/openAccount")
    public String openAccount(@RequestBody RegisterAccountDTO request){
        return accountService.openAccount(request);
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
