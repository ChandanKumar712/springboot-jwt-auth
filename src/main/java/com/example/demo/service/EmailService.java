package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) {

        String verificationLink =
                "http://localhost:8082/verify?token=" + token;

        System.out.println("TOKEN GENERATED: " + token);
        System.out.println("VERIFICATION LINK: " + verificationLink);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);

        message.setSubject("Verify Your Account");

        message.setText(
                "Hello,\n\n" +
                        "Click the link below to verify your account:\n\n" +
                        verificationLink +
                        "\n\nThank You!"
        );

        System.out.println("MAIL SENDING STARTED");

        mailSender.send(message);

        System.out.println("MAIL SENT SUCCESSFULLY");
    }

    //// For OTP Verification
    public void sendOtpEmail(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Your Login OTP");
        message.setText(
                "Hello,\n\n" +
                        "Your OTP for login is: " + otp +
                        "\n\nThis OTP is valid for 5 minutes." +
                        "\n\nThank You!"
        );

        mailSender.send(message);
    }


}