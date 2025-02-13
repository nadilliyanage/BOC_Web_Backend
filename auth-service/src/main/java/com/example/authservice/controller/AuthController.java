package com.example.authservice.controller;

import com.example.authservice.dto.LoginRequestDTO;
import com.example.authservice.dto.SignUpRequestDTO;
import com.example.authservice.model.UserTable;
import com.example.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        boolean isValid = authService.validateUser(loginRequest.getUserId(), loginRequest.getPassword());
        if (isValid) {
            UserTable user = authService.getUserDetails(loginRequest.getUserId());
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO signUpRequest) {
        boolean isValid = authService.validateCredentials(signUpRequest.getUserId(), signUpRequest.getPassword());
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        UserTable newUser = new UserTable();
        newUser.setUserId(signUpRequest.getUserId());
        newUser.setName(signUpRequest.getName());
        newUser.setRole("USER"); // Default role for new users

        authService.saveUser(newUser);
        return ResponseEntity.ok("User registered successfully");
    }
}