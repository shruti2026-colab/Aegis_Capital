package com.aegiscapital.respository;

import com.aegiscapital.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // helps to find user by checking their id
    List<Account> findByUserId(Long userId);
}
