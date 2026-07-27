package com.cognizant.jwt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration for JWT Authentication Service
 * 
 * This configuration:
 * 1. Configures the authentication controller
 * 2. Disables CSRF for REST API
 * 3. Allows authentication endpoint to be accessible
 * 4. Sets session management to stateless
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * Configure Security Filter Chain
     * 
     * @param http HttpSecurity configuration
     * @return SecurityFilterChain
     * @throws Exception If configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring Security Filter Chain for JWT Authentication Service");
        
        http
            // Disable CSRF as we're using JWT tokens
            .csrf(csrf -> csrf.disable())
            
            // Disable all authentication and authorization - allow all requests
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll()
            )
            
            // Configure session management to be stateless (JWT based)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Disable all authentication mechanisms
            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable());
        
        logger.info("Security configuration completed successfully");
        return http.build();
    }
}
