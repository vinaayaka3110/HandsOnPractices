package com.cognizant.jwt.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.jwt.model.JwtResponse;
import com.cognizant.jwt.util.JwtUtil;

/**
 * Authentication Controller
 * Handles JWT authentication and token generation
 * 
 * This controller implements the three major steps:
 * 1. Create authentication controller and configure it in SecurityConfig
 * 2. Read Authorization header and decode the username and password
 * 3. Generate token based on the user retrieved in the previous step
 */
@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authentication endpoint that generates and returns JWT token
     * 
     * Request: curl -s -u user:pwd http://localhost:8090/authenticate
     * Response: {"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNTcwMzc5NDc0LCJleHAiOjE1NzAzODA2NzR9.t3LRvlCV-hwKfoqZYlaVQqEUiBloWcWn0ft3tgv0dL0"}
     * 
     * Steps:
     * 1. Read Authorization header and decode the username and password
     * 2. Validate credentials (for demo, accept user:pwd)
     * 3. Generate token based on the user retrieved
     * 
     * @param authorizationHeader Authorization header with Basic credentials
     * @return JWT token response
     */
    @GetMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        logger.info("START - authenticate() method called");
        logger.info("Processing authentication request");
        
        try {
            // Step 1: Read Authorization header and decode the username and password
            logger.info("Reading and decoding Authorization header");
            
            if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
                logger.warn("Invalid Authorization header format");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            // Extract credentials from Authorization header
            String base64Credentials = authorizationHeader.substring(6); // Remove "Basic "
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedBytes, StandardCharsets.UTF_8);
            
            // Split username and password
            String[] values = credentials.split(":", 2);
            if (values.length != 2) {
                logger.warn("Invalid credentials format");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            String username = values[0];
            String password = values[1];
            
            logger.info("Extracted credentials - Username: {}", username);
            logger.debug("Password provided for user: {}", username);
            
            // Step 2: Validate credentials (for demo purposes, accept user:pwd)
            if (!isValidCredentials(username, password)) {
                logger.warn("Invalid credentials provided for user: {}", username);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            logger.info("Credentials validated successfully for user: {}", username);
            
            // Step 3: Generate token based on the user retrieved in the previous step
            logger.info("Generating JWT token for user: {}", username);
            String token = jwtUtil.generateToken(username);
            
            JwtResponse response = new JwtResponse(token);
            
            logger.info("JWT token generated successfully for user: {}", username);
            logger.info("END - authenticate() method completed - Token generated");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            logger.error("Error during authentication: " + e.getMessage());
            logger.info("END - authenticate() method completed with error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Validate user credentials
     * For demo purposes, accepts:
     * - user:pwd
     * - admin:admin
     * - test:test
     * 
     * @param username Username
     * @param password Password
     * @return True if valid, false otherwise
     */
    private boolean isValidCredentials(String username, String password) {
        logger.debug("Validating credentials for user: {}", username);
        
        // For demo purposes, accept these hardcoded credentials
        return ("user".equals(username) && "pwd".equals(password)) ||
               ("admin".equals(username) && "admin".equals(password)) ||
               ("test".equals(username) && "test".equals(password));
    }

    /**
     * Health check endpoint
     * 
     * @return Health status
     */
    @GetMapping("/health")
    public String health() {
        logger.info("Health check endpoint accessed");
        return "JWT Authentication Service is UP and running on port 8090!";
    }

    /**
     * API info endpoint with usage information
     * 
     * @return Usage information
     */
    @GetMapping("/api/info")
    public String apiInfo() {
        logger.info("API info endpoint accessed");
        return "JWT Authentication Service\n\n" +
               "Usage:\n" +
               "curl -s -u user:pwd http://localhost:8090/authenticate\n\n" +
               "Valid credentials:\n" +
               "- user:pwd\n" +
               "- admin:admin\n" +
               "- test:test\n\n" +
               "Response format:\n" +
               "{\"token\":\"jwt_token_here\"}";
    }
}
