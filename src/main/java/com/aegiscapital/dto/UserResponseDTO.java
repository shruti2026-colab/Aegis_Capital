package com.aegiscapital.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String userId;
    private String name;
    private String email;
    private String mobileNumber;
    private String role;
}