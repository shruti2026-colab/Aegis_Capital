package com.aegiscapital.controller;

import com.aegiscapital.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/deposit")
    public String deposit(@RequestParam Long accountId,
                          @RequestParam BigDecimal amount) {

        accountService.deposit(accountId, amount);
        return "Deposit successful";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam Long accountId,
                           @RequestParam BigDecimal amount) {

        accountService.withdraw(accountId, amount);
        return "Withdraw successful";
    }

    @GetMapping("/{id}/balance")
    public BigDecimal getBalance(@PathVariable Long id) {

        return accountService.getBalance(id);
    }
}
