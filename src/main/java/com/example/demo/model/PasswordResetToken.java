package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;   // will now store OTP

    private String username;   // will now store email

    private LocalDateTime expiryTime;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, String username, LocalDateTime expiryTime) {
        this.token = token;
        this.username = username;
        this.expiryTime = expiryTime;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

}