package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/verify")
    public String verifyUser(
            @RequestParam("token") String token
    ) {

        System.out.println("TOKEN RECEIVED: " + token);

        VerificationToken verificationToken =
                tokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "Invalid Token";
        }

        User user =
                userRepository.findByUsername(
                        verificationToken.getUsername()
                );

        if (user == null) {
            return "User Not Found";
        }

        user.setEnabled(true);

        userRepository.save(user);

        return "Email Verified Successfully";
    }
}