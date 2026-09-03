/*package com.example.demo.service;

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

 */








//////      New Updated SMTP with Brevo Email API

package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    private final RestTemplate restTemplate = new RestTemplate();

    private void sendEmail(String toEmail, String subject, String htmlContent) {

        String url = "https://api.brevo.com/v3/smtp/email";

        Map<String, Object> sender = new HashMap<>();
        sender.put("name", "Secure User Auth");
        sender.put("email", senderEmail);

        Map<String, Object> recipient = new HashMap<>();
        recipient.put("email", toEmail);

        Map<String, Object> body = new HashMap<>();
        body.put("sender", sender);
        body.put("to", List.of(recipient));
        body.put("subject", subject);
        body.put("htmlContent", htmlContent);

        HttpHeaders headers = new HttpHeaders();
       // headers.set("api-key", brevoApiKey);
        headers.set("api-key", brevoApiKey.trim());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accept", "application/json");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, String.class);

        System.out.println("EMAIL SENT via Brevo to: " + toEmail);
    }

    public void sendVerificationEmail(String toEmail, String token) {

        String verificationLink =
                "http://localhost:8082/verify?token=" + token;

        sendEmail(
                toEmail,
                "Verify Your Account",
                "Hello,<br><br>Click the link below to verify your account:<br><br>" +
                        verificationLink + "<br><br>Thank You!"
        );
    }

    public void sendOtpEmail(String toEmail, String otp) {

        sendEmail(
                toEmail,
                "Your Login OTP",
                "Hello,<br><br>Your OTP for login is: <b>" + otp + "</b>" +
                        "<br><br>This OTP is valid for 5 minutes.<br><br>Thank You!"
        );
    }
}