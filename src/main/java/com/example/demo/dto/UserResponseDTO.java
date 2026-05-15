package com.example.demo.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;

    // For profile API
    private String role;
}
