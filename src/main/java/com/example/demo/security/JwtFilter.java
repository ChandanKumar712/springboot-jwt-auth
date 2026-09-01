package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;

// For Role-Based Authorization.....
import io.jsonwebtoken.Claims;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();   // CHANGE

        System.out.println("REQUEST PATH: " + path); // debug

      //  return path.contains("/login") || path.contains("/signup");

        //// Swagger/OpenAPI for interactive API documentation and API testing.
        return path.contains("/login")
                || path.contains("/signup")
                || path.contains("/refresh")
              //  || path.contains("/verify")
                || path.contains("/verify-login-otp")
                || path.contains("/forgot-password")
                || path.contains("/reset-password")
                || path.equals("/api/user-logout")
                || path.contains("/swagger-ui")
                || path.contains("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        ////   LOGIN Bypass
//        if (path.contains("/login") || path.contains("/signup")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        ////  For Swagger/OpenAPI
        if (path.contains("/login")
                || path.contains("/signup")
                || path.contains("/refresh")
               // || path.contains("/verify")
                || path.contains("/verify-login-otp")
                || path.contains("/forgot-password")
                || path.contains("/reset-password")
                || path.equals("/api/user-logout")
                || path.contains("/swagger-ui")
                || path.contains("/v3/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing Token");
            return;
        }

        String token = authHeader.substring(7);

        try {
//            String username = JwtUtil.validateToken(token).getSubject();
//
//            UsernamePasswordAuthenticationToken auth =
//                    new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
//
//            SecurityContextHolder.getContext().setAuthentication(auth);


            Claims claims = JwtUtil.validateToken(token);

            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.singletonList(
                                    new SimpleGrantedAuthority("ROLE_" + role)
                            )
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}