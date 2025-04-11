package com.example.authservice.service;

import com.example.authservice.dto.UserDTO;
import com.example.authservice.model.UserTable;
import com.example.authservice.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    // Method to get all active users
    public List<UserDTO> getAllUsers() {
        // Fetch all users from the repository
        List<UserTable> userList = userRepo.findAll();

        // Filter out users with the role "SUPERADMIN" and only include ACTIVE users
        List<UserTable> filteredUsers = userList.stream()
                .filter(user -> !"SUPERADMIN".equals(user.getRole()) && "ACTIVE".equals(user.getStatus()))
                .collect(Collectors.toList());

        // Map the filtered list to UserDTO
        return modelMapper.map(filteredUsers, new TypeToken<List<UserDTO>>() {
        }.getType());
    }

    // Method to get all deleted users
    public List<UserDTO> getDeletedUsers() {
        // Fetch all users from the repository
        List<UserTable> userList = userRepo.findAll();

        // Filter out users with the role "SUPERADMIN" and only include DELETED users
        List<UserTable> filteredUsers = userList.stream()
                .filter(user -> !"SUPERADMIN".equals(user.getRole()) && "DELETED".equals(user.getStatus()))
                .collect(Collectors.toList());

        // Map the filtered list to UserDTO
        return modelMapper.map(filteredUsers, new TypeToken<List<UserDTO>>() {
        }.getType());
    }

    // Method to save a new user
    public UserDTO saveUser(UserDTO userDTO) {
        // Convert DTO to entity
        UserTable user = modelMapper.map(userDTO, UserTable.class);

        // Set status to ACTIVE for new users
        user.setStatus("ACTIVE");

        // Save the user (id will be auto-generated)
        UserTable savedUser = userRepo.save(user);

        // Convert the saved entity back to DTO
        return modelMapper.map(savedUser, UserDTO.class);
    }

    // Method to update a user
    public UserDTO updateUser(UserDTO userDTO) {
        // Convert DTO to entity
        UserTable user = modelMapper.map(userDTO, UserTable.class);

        // Update the user
        UserTable savedUser = userRepo.save(user);

        // Convert the saved entity back to DTO
        return modelMapper.map(savedUser, UserDTO.class);
    }

    // Method to soft delete a user by their ID
    public void deleteUserById(int id) {
        UserTable user = userRepo.getUserById(id);
        if (user != null) {
            user.setStatus("DELETED");
            userRepo.save(user);
        }
    }

    // Method to restore a deleted user
    public void restoreUserById(int id) {
        UserTable user = userRepo.getUserById(id);
        if (user != null) {
            user.setStatus("ACTIVE");
            userRepo.save(user);
        }
    }

    // Method to get a user by their ID
    public UserDTO getUserById(int id) {
        UserTable user = userRepo.getUserById(id);
        return modelMapper.map(user, UserDTO.class);
    }

    // Method to check if a userId already exists
    public boolean doesUserIdExist(String userId) {
        return userRepo.existsByUserId(userId);
    }

    // Method to get the count of users
    public long getCountOfUsers() {
        return userRepo.count();
    }
}