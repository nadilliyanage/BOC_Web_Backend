package com.example.authservice.service;

import com.example.authservice.model.ADTable;
import com.example.authservice.model.UserTable;
import com.example.authservice.repo.ADTableRepository;
import com.example.authservice.repo.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ADTableRepository adTableRepository;

    @Autowired
    private UserTableRepository userTableRepository;

    // Validate user credentials
    public boolean validateUser(String userId, String password) {
        Optional<ADTable> adUser = adTableRepository.findByUserId(userId);
        return adUser.isPresent() && adUser.get().getPassword().equals(password);
    }

    // Validate credentials for sign-up
    public boolean validateCredentials(String userId, String password) {
        Optional<ADTable> adUser = adTableRepository.findByUserId(userId);
        return adUser.isPresent() && adUser.get().getPassword().equals(password);
    }

    // Save user details
    public void saveUser(UserTable user) {
        // Save the user with the role in plaintext
        userTableRepository.save(user);
    }

    // Retrieve user details
    public UserTable getUserDetails(String userId) {
        return userTableRepository.findByUserId(userId).orElse(null);
    }
}