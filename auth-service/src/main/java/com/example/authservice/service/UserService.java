package com.example.authservice.service;


import com.example.authservice.dto.UserDTO;
import com.example.authservice.model.User;
import com.example.authservice.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    // Method to get all users
    public List<UserDTO> getAllUsers() {
        List<User> userList = userRepo.findAll();
        return modelMapper.map(userList, new TypeToken<List<UserDTO>>() {}.getType());
    }

    // Method to save a new user
    public UserDTO saveUser(UserDTO userDTO) {
        // Convert DTO to entity
        User user = modelMapper.map(userDTO, User.class);

        // Save the user (id will be auto-generated)
        User savedUser = userRepo.save(user);

        // Convert the saved entity back to DTO
        return modelMapper.map(savedUser, UserDTO.class);
    }

    // Method to update a user
    public UserDTO updateUser(UserDTO userDTO) {
        // Convert DTO to entity
        User user = modelMapper.map(userDTO, User.class);

        // Update the user
        User savedUser = userRepo.save(user);

        // Convert the saved entity back to DTO
        return modelMapper.map(savedUser, UserDTO.class);
    }

    // Method to delete a user by their ID
    public void deleteUserById(int id) {
        userRepo.deleteById(id);
    }

    // Method to get a user by their ID
    public UserDTO getUserById(int id) {
        User user = userRepo.getUserById(id);
        return modelMapper.map(user, UserDTO.class);
    }

    // Method to check if a userId already exists
    public boolean doesUserIdExist(String userId) {
        return userRepo.existsByUserId(userId);
    }
}