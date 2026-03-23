package com.aegiscapital.IdGenerator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGeneratorImpl
{
    //generating accout id in format AGS-XXXXXXXX
    public String generateAccountNumber(){
        return "AGS-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    //generating transaction id in format TXN-XXXXXXXX
    public String generateTransactionId(){
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }

    //generating user id in format <first_3_chars of username>-XXXXXXXX
    public String generateUserId(String name){
        String prefix = name.substring(0,Math.min(3, name.length())).toUpperCase();
        return prefix + "-" + UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();
    }
}


