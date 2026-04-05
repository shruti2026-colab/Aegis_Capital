package com.aegiscapital.repository;

import com.aegiscapital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // helps to find user details by email
    Optional<User> findByEmail(String email);


    // checks if user exists with the email we looking for
    boolean existsByEmail(String email);

    User findByUserId(String userId);
}
