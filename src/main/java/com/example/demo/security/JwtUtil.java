//package com.example.demo.security;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//
//import java.security.Key;
//import java.util.Date;
//
//public class JwtUtil {
//
//    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//    // token generate
//    public static String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
//                .signWith(SECRET_KEY)
//                .compact();
//    }
//
//    // token validate
//    public static String validateToken(String token) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
//    }
//}







// using DTO for JWT
//package com.example.demo.security;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//
//import java.security.Key;
//import java.util.Date;
//
//public class JwtUtil {
//
//    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//
//    public static String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
//                .signWith(SECRET_KEY)
//                .compact();
//    }
//    public static String validateToken(String token) {
//
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
//    }
//}



/////    for user token

package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Secret key
    private static final String SECRET =
            "mySuperSecretKeyForJwtTokenGeneration123456";

    // Generate signing key
    private static final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    // ACCESS TOKEN
    public static String generateToken(
            String username,
            String role
    ) {

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())

                // 1 hour
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )

                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // REFRESH TOKEN
    public static String generateRefreshToken(
            String username
    ) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())

                // 7 days
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000L * 60 * 60 * 24 * 7
                        )
                )

                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // VALIDATE TOKEN
    public static Claims validateToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // EXTRACT USERNAME
    public static String extractUsername(String token) {

        return validateToken(token)
                .getSubject();
    }
}