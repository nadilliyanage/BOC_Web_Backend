package com.example.authservice.controller;

import com.example.authservice.dto.UserTypeDTO;
import com.example.authservice.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/user-types")
public class UserTypeController {

    @Autowired
    private UserTypeService userTypeService;

    // Get all User Types
    @GetMapping
    public ResponseEntity<List<UserTypeDTO>> getAllUserTypes() {
        return ResponseEntity.ok(userTypeService.getAllUserTypes());
    }

    // Save a new User Type
    @PostMapping
    public ResponseEntity<UserTypeDTO> saveUserType(@RequestBody UserTypeDTO userTypeDTO) {
        return ResponseEntity.ok(userTypeService.saveUserType(userTypeDTO));
    }

    // Update an existing User Type
    @PutMapping("/{id}")
    public ResponseEntity<UserTypeDTO> updateUserType(
            @PathVariable Integer id,
            @RequestBody UserTypeDTO userTypeDTO
    ) {
        userTypeDTO.setId(id); // Ensure ID is set for the update
        return ResponseEntity.ok(userTypeService.updateUserType(userTypeDTO));
    }

    // Delete a User Type by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserType(@PathVariable Integer id) {
        userTypeService.deleteUserTypeById(id);
        return ResponseEntity.noContent().build();
    }
}
