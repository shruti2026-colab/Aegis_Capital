package com.aegiscapital.controller;

import com.aegiscapital.dto.AccountAdminDTO;
import com.aegiscapital.dto.SuccessResponseDTO;
import com.aegiscapital.dto.TransactionAdminDTO;
import com.aegiscapital.dto.UserResponseDTO;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.Transaction;
import com.aegiscapital.entity.User;
import com.aegiscapital.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 1. Get all users
    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers() {
        return adminService.getAllUsers();
    }

    // 2. Get all accounts

    @GetMapping("/accounts")
    public List<AccountAdminDTO> getAllAccounts() {
        return adminService.getAllAccounts();
    }

    @GetMapping("/transactions")
    public List<TransactionAdminDTO> getAllTransactions() {
        return adminService.getAllTransactions();
    }


    // 4. Deactivate account
    @DeleteMapping("/account/{accountNumber}")
    public ResponseEntity<SuccessResponseDTO<Void>> deleteAccount(@PathVariable String accountNumber) {
        adminService.deleteAccount(accountNumber);

        SuccessResponseDTO<Void> response = new SuccessResponseDTO<>(
                200,
                "Account Deactivated Successful",
                null
        );
        return ResponseEntity.ok(response);
    }

    // 5. Promote user to admin
    @PutMapping("/promote/{email}")
    public ResponseEntity<SuccessResponseDTO<Void>> promoteUser(@PathVariable String email) {
        adminService.promoteToAdmin(email);
        SuccessResponseDTO<Void> response = new SuccessResponseDTO<>(
                200,
                "User promoted to ADMIN",
                null
        );
        return ResponseEntity.ok(response);
    }
}