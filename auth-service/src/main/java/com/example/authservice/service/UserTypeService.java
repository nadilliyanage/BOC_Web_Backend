package com.example.authservice.service;

import com.example.authservice.dto.UserTypeDTO;
import com.example.authservice.model.UserType;
import com.example.authservice.repo.UserTypeRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserTypeService {

    @Autowired
    private UserTypeRepo userTypeRepo;

    @Autowired
    private ModelMapper modelMapper;

    // Fetch all User Types
    public List<UserTypeDTO> getAllUserTypes() {
        List<UserType> userTypes = userTypeRepo.findAll();
        return modelMapper.map(userTypes, new TypeToken<List<UserTypeDTO>>() {}.getType());
    }

    // Save a new User Type
    public UserTypeDTO saveUserType(UserTypeDTO userTypeDTO) {
        UserType userType = modelMapper.map(userTypeDTO, UserType.class);
        UserType savedUserType = userTypeRepo.save(userType);
        return modelMapper.map(savedUserType, UserTypeDTO.class);
    }

    // Update an existing User Type
    public UserTypeDTO updateUserType(UserTypeDTO userTypeDTO) {
        if (userTypeDTO.getId() == null || !userTypeRepo.existsById(userTypeDTO.getId())) {
            throw new IllegalArgumentException("UserType ID not found or not provided");
        }
        UserType userType = modelMapper.map(userTypeDTO, UserType.class);
        UserType updatedUserType = userTypeRepo.save(userType);
        return modelMapper.map(updatedUserType, UserTypeDTO.class);
    }

    // Delete a User Type by ID
    public void deleteUserTypeById(Integer id) {
        if (!userTypeRepo.existsById(id)) {
            throw new IllegalArgumentException("UserType ID not found");
        }
        userTypeRepo.deleteById(id);
    }
}
