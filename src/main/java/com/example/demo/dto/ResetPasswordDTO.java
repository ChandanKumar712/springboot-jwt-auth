//////  This is just for Email verification to reset/forget password
//package com.example.demo.dto;
//
//public class ResetPasswordDTO {
//
//    private String token;
//
//    private String newPassword;
//
//    public String getToken() {
//        return token;
//    }
//
//    public String getNewPassword() {
//        return newPassword;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public void setNewPassword(String newPassword) {
//        this.newPassword = newPassword;
//    }
//}
//




///////    This is for The OTP based verification to reset/forget the password

package com.example.demo.dto;

public class ResetPasswordDTO {

    private String email;
    private String otp;
    private String newPassword;

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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}