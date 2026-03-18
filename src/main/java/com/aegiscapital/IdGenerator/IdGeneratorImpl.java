package com.aegiscapital.IdGenerator;

import org.springframework.stereotype.Component;

@Component
public class IdGeneratorImpl
{
    //generating accout id in format AGSXXXXX1
    public String generateAccountId(Long id){
        return "AGS" + String.format("%06d", id);
    }

    //generating transaction id in format TXNXXXXX1
    public String generateTransactionId(Long id){
        return "TXN" + String.format("%06d", id);
    }

    //generating user id in format <first_3_chars of username>XXXXX1
    public String generateUserId(String name, Long id){
        String prefix = name.substring(0,Math.min(3, name.length())).toUpperCase();
        return prefix + String.format("%06d", id);
    }
}
