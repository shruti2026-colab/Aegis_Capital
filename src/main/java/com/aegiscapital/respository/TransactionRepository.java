package com.aegiscapital.respository;

import com.aegiscapital.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long > {

    // to find transaction details sender account to receiver account
    List<Transaction> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);

    // find transactions of particular sender account
    List<Transaction> findByFromAccountId(Long accountId);

    // find transactions of particular receivers account
    List<Transaction> findByToAccountId(Long accountId);
}
