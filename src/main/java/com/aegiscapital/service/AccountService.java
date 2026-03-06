package com.aegiscapital.service;

import java.math.BigDecimal;

public interface AccountService
{
    void deposit(Long accountId, BigDecimal amount);
    void withdraw(Long accountId, BigDecimal amount);
    BigDecimal getBalance(Long accountId);
}
