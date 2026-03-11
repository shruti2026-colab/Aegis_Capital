package com.aegiscapital.controller;

import com.aegiscapital.dto.TransactionResponseDTO;
import com.aegiscapital.dto.TransferRequestDTO;
import com.aegiscapital.entity.Transaction;
import com.aegiscapital.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequestDTO request){
        transactionService.transferFunds(request);
        return ResponseEntity.ok("Funds transferred successfully");
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(@PathVariable Long accountId) {
        return ResponseEntity.ok(transactionService.getTransactions(accountId));
    }

}
