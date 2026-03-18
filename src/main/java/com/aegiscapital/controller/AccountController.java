package com.aegiscapital.controller;

import com.aegiscapital.dto.DepositRequestDTO;
import com.aegiscapital.dto.WithdrawRequestDTO;
import com.aegiscapital.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    //deposit api with account id and amount as input
    @PostMapping("/deposit")
    public String deposit(@RequestBody DepositRequestDTO request) {

        accountService.deposit(request.getAccountId(), request.getAmount());
        return "Deposit successful";
    }

    //withdraw api with account id, password and amount as input
    @PostMapping("/withdraw")
    public String withdraw(@RequestBody WithdrawRequestDTO request) {

        accountService.withdraw(request.getAccountId(), request.getAmount());
        return "Withdraw successful";
    }

    //get balance api with account id as input
    @GetMapping("/{id}/balance")
    public BigDecimal getBalance(@PathVariable Long id) {

        return accountService.getBalance(id);
    }
}
