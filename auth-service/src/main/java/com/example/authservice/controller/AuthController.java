package com.example.authservice.controller;

import com.example.authservice.dto.LoginRequestDTO;
import com.example.authservice.dto.SignUpRequestDTO;
import com.example.authservice.model.UserTable;
import com.example.authservice.service.AuthService;
import com.example.authservice.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger loginLogger = LoggerFactory.getLogger("loginLogger");

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        // Validate user credentials
        boolean isValid = authService.validateUser(loginRequest.getUserId(), loginRequest.getPassword());

        if (!isValid) {
            loginLogger.warn("Failed login attempt - UserID: {}", loginRequest.getUserId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // If valid, proceed with login
        UserTable user = authService.getUserDetails(loginRequest.getUserId());
        if (user == null) {
            loginLogger.error("User not found after validation - UserID: {}", loginRequest.getUserId());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not found");
        }

        String token = jwtUtil.generateToken(user.getUserId(), user.getRole(), user.getName(), user.getId());

        loginLogger.info("Successful login - UserID: {}, Role: {}", user.getUserId(), user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getUserId());
        response.put("userName", user.getName());
        response.put("userRole", user.getRole());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO signUpRequest) {
        // First check if user already exists
        if (authService.userExists(signUpRequest.getUserId())) {
            loginLogger.warn("Signup attempt with existing UserID: {}", signUpRequest.getUserId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

        // Validate credentials against AD or other auth source
        boolean isValid = authService.validateCredentials(signUpRequest.getUserId(), signUpRequest.getPassword());
        if (!isValid) {
            loginLogger.warn("Failed sign-up attempt - Invalid credentials for UserID: {}", signUpRequest.getUserId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Create new user
        UserTable newUser = new UserTable();
        newUser.setUserId(signUpRequest.getUserId());
        newUser.setName(signUpRequest.getName());
        newUser.setDepartment(signUpRequest.getDepartment());
        newUser.setRole("USER"); // Default role for new users

        try {
            authService.saveUser(newUser);
            loginLogger.info("Successful sign-up - UserID: {}, Name: {}, Role: {}",
                    newUser.getUserId(), newUser.getName(), newUser.getRole());

            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            loginLogger.error("Error during user registration - UserID: {}, Error: {}",
                    signUpRequest.getUserId(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed");
        }
    }
}