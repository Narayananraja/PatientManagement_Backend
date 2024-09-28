package com.patient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF protection
                .cors(cors -> cors.disable()) // Enable CORS support
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/patients/login", "/api/patients/register","/api/patients/**","/api/patients/check-email/**").permitAll()  // Permit these endpoints
                        .anyRequest().authenticated()  // Require authentication for all other requests
                )
                .httpBasic(httpBasic -> {

                });

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Using BCryptPasswordEncoder
    }
}
