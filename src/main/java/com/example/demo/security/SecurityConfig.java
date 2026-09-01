//package com.example.demo.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/signup", "/login").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}
//





////    For Swagger/OpenAPI
package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//// Added for fronted
import org.springframework.security.config.Customizer;
import org.springframework.http.HttpMethod;

///   For Rate limiter
import com.example.demo.ratelimit.RateLimitFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private RateLimitFilter rateLimitFilter;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//
//                .authorizeHttpRequests(auth -> auth
//
//                        // public APIs
//                        .requestMatchers(
//                                "/signup",
//                                "/login",
//                                 "/refresh",
//                                 "/api/logout",
//                                // swagger
//                                "/swagger-ui/**",
//                                "/v3/api-docs/**",
//                                "/swagger-ui.html"
//                        ).permitAll()
//
//                        .anyRequest().authenticated()
//                )
//
//                .addFilterBefore(jwtFilter,
//                        UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }



    ////    For USER Logout
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                // For frontend connect ==> allow to use the security to CORS config
                .cors(Customizer.withDefaults())

                .logout(logout -> logout.disable())

                .authorizeHttpRequests(auth -> auth

                        // For frontend connect ==> preflight allow
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(
                                "/signup",
                                "/login",
                                "/refresh",
                              //  "/verify",
                                "/verify-login-otp",
                                "/forgot-password",
                                "/reset-password",
                                "/api/user-logout",

                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        rateLimitFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

}