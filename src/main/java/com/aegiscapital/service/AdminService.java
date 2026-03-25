package com.aegiscapital.service;



import com.aegiscapital.dto.AccountAdminDTO;
import com.aegiscapital.dto.TransactionAdminDTO;
import com.aegiscapital.dto.UserResponseDTO;
import com.aegiscapital.entity.Account;
import com.aegiscapital.entity.Transaction;
import com.aegiscapital.entity.User;

import java.util.List;

public interface AdminService {



    List<UserResponseDTO> getAllUsers();


    List<AccountAdminDTO> getAllAccounts();
    List<TransactionAdminDTO> getAllTransactions();

    void deleteAccount(String accountNumber);

    void promoteToAdmin(String email);
}