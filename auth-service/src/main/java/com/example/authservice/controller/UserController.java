package com.example.authservice.controller;

import com.example.authservice.dto.UserDTO;
import com.example.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to get all active users
    @GetMapping("/getusers")
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    // Endpoint to get all deleted users
    @GetMapping("/getdeletedusers")
    public List<UserDTO> getDeletedUsers() {
        return userService.getDeletedUsers();
    }

    // Endpoint to add a new user
    @PostMapping("/adduser")
    public UserDTO saveUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    // Endpoint to get a user by their ID
    @GetMapping("/user/{id}")
    public UserDTO getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // Endpoint to update a user
    @PutMapping("/updateuser")
    public UserDTO updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    // Endpoint to soft delete a user by their ID
    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User marked as deleted successfully!");
    }

    // Endpoint to restore a deleted user
    @PutMapping("/restoreuser/{id}")
    public ResponseEntity<String> restoreUser(@PathVariable int id) {
        userService.restoreUserById(id);
        return ResponseEntity.ok("User restored successfully!");
    }

    // Endpoint to check if a userId already exists
    @GetMapping("/check-user-id/{userId}")
    public ResponseEntity<?> checkUserIdExists(@PathVariable String userId) {
        boolean exists = userService.doesUserIdExist(userId);
        if (exists) {
            return ResponseEntity.status(409).body("User ID already exists.");
        } else {
            return ResponseEntity.status(200).body("User ID is available.");
        }
    }

    // Endpoint to get the count of users
    @GetMapping("/user-count")
    public ResponseEntity<Long> getCountOfUsers() {
        long userCount = userService.getCountOfUsers();
        return ResponseEntity.ok(userCount);
    }
}
