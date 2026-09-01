////    This is for just email Verification which is not suitable now


//package com.example.demo.dto;
//
//public class ForgotPasswordRequestDTO {
//
//    private String username;
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//}


//////    This is for OTP based verification to forgot password
package com.example.demo.dto;

public class ForgotPasswordRequestDTO {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}