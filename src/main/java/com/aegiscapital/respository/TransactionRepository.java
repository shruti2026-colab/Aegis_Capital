package com.aegiscapital.respository;

import com.aegiscapital.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long > {

    // to find transaction details sender account to receiver account
    List<Transaction> findByFromAccountNumberOrToAccountNumber(String fromAccountNumber, String toAccountNumber);

    // find transactions of particular sender account
    List<Transaction> findByFromAccountNumber(String accountNumber);

    // find transactions of particular receivers account
    List<Transaction> findByToAccountNumber(String accountNumber);


    List<Transaction> findByFromAccountNumberOrToAccountNumberOrderByTimestampDesc(String fromAccountNumber, String toAccountNumber);
}


