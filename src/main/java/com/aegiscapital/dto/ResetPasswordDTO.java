package com.aegiscapital.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    public String userId;
    public String accountNumber;
    public String email;
    public String mobileNumber;
}
