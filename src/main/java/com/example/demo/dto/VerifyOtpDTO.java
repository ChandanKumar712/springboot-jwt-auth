package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class VerifyOtpDTO {

    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "OTP cannot be empty")
    private String otp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}