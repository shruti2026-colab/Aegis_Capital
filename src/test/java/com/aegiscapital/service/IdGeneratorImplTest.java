package com.aegiscapital.service;

import com.aegiscapital.IdGenerator.IdGeneratorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IdGeneratorImplTest {

    private final IdGeneratorImpl idGenerator = new IdGeneratorImpl();


    // test account number generation
    @Test
    void testGenerateAccountNumber(){
        String accountNumber = idGenerator.generateAccountNumber();
        // check if it generates null
        assertNotNull(accountNumber);
        // check if account number start with AGS-
        assertTrue(accountNumber.startsWith("AGS-"));
        //check if account number length is = 14
        assertEquals(14, accountNumber.length());
        // check -pattern
        assertTrue(accountNumber.matches("AGS-[A-Z0-9]{10}"));
    }

    @Test
    void testGenerateTransactionId(){
        String transactionId = idGenerator.generateTransactionId();
        // check if it generates null
        assertNotNull(transactionId);
        // check if TransactionId  start with TXN-
        assertTrue(transactionId.startsWith("TXN-"));
        //check if TransactionId length is = 14
        assertEquals(14, transactionId.length());
        // check -pattern
        assertTrue(transactionId.matches("TXN-[A-Z0-9]{10}"));
    }

    @Test
    void testGenerateUserId(){
        String userId = idGenerator.generateUserId("anil");
        // check if it generates null
        assertNotNull(userId);
        // check if account number start with ANI- & first 3 letters upercase
        assertTrue(userId.startsWith("ANI-"));
        //check if account number length is = 14
        assertEquals(12, userId.length());
        // check -pattern
        assertTrue(userId.matches("ANI-[A-Z0-9]{8}"));
    }

}
