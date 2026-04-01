package com.aegiscapital.dto;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDTO {
    private String email;
    private String password;

    public LoginRequestDTO(String mail, String number) {
    }
}