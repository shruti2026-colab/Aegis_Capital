package com.aegiscapital.repository;

import com.aegiscapital.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // helps to find user by checking their id
    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByUser_UserId(String userId);
}

