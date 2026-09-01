//package com.example.demo.ratelimit;
//
//import io.github.bucket4j.Bucket;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class RateLimitFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private RateLimitConfig rateLimitConfig;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        String ip = request.getRemoteAddr();
//
//        Bucket bucket = rateLimitConfig.resolveBucket(ip);
//
//        // LOGIN API/LIMIT
//        if (request.getRequestURI().contains("/login")) {
//
//            if (bucket.tryConsume(1)) {
//
//                filterChain.doFilter(request, response);
//
//            } else {
//
//                response.setStatus(429);
//
//                response.getWriter().write(
//                        "Too many login attempts. Try again later."
//                );
//            }
//
//        } else {
//
//            filterChain.doFilter(request, response);
//        }
//    }
//}





//////   New Updated
package com.example.demo.ratelimit;

import io.github.bucket4j.Bucket;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    @Autowired
    private RateLimitConfig rateLimitConfig;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String ip = request.getRemoteAddr();

        Bucket bucket = rateLimitConfig.resolveBucket(ip);

        // LOGIN API/LIMIT
        if (request.getRequestURI().contains("/login")) {

            if (bucket.tryConsume(1)) {

                filterChain.doFilter(request, response);

            } else {

                response.setStatus(429);
                response.getWriter().write(
                        "Too many login attempts. Try again later."
                );
            }

        } else {

            filterChain.doFilter(request, response);
        }
    }
}