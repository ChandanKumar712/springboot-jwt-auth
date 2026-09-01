package com.example.demo.repository;

import com.example.demo.model.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {

    OtpToken findTopByEmailOrderByIdDesc(String email);
}