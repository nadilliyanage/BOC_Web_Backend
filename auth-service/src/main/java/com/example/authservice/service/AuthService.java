package com.example.authservice.service;

import com.example.authservice.model.ADTable;
import com.example.authservice.model.UserTable;
import com.example.authservice.repo.ADTableRepository;
import com.example.authservice.repo.UserTableRepository;
import com.example.authservice.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// AuthService.java
@Service
public class AuthService {
    @Autowired
    private ADTableRepository adTableRepository;

    @Autowired
    private UserTableRepository userTableRepository;

    public boolean validateUser(String userId, String password) {
        Optional<ADTable> adUser = adTableRepository.findByUserId(userId);
        return adUser.isPresent() && adUser.get().getPassword().equals(password);
    }

    public boolean validateCredentials(String userId, String password) {
        Optional<ADTable> adUser = adTableRepository.findByUserId(userId);
        return adUser.isPresent() && adUser.get().getPassword().equals(password);
    }

    public void saveUser(UserTable user) {
        // Encrypt the role before saving
        String encryptedRole = EncryptionUtil.encrypt(user.getRole());
        user.setRole(encryptedRole);
        userTableRepository.save(user);
        String decryptedRole = EncryptionUtil.decrypt(user.getRole());
        user.setRole(decryptedRole);
    }

    public UserTable getUserDetails(String userId) {
        UserTable user = userTableRepository.findByUserId(userId).orElse(null);
        if (user != null) {
            // Decrypt the role before returning
            String decryptedRole = EncryptionUtil.decrypt(user.getRole());
            user.setRole(decryptedRole);
        }
        return user;
    }
}