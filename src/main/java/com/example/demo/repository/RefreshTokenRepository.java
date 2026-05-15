//package com.example.demo.repository;
//
//import com.example.demo.model.RefreshToken;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface RefreshTokenRepository
//        extends JpaRepository<RefreshToken, Long> {
//
//    RefreshToken findByRefreshToken(String refreshToken);
//
//   // void deleteByUsername(String username);
//    void deleteByRefreshToken(String refreshToken);
//}




package com.example.demo.repository;

import com.example.demo.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Transactional
    @Modifying
    void deleteByRefreshToken(String refreshToken);
}