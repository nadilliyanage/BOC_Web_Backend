package com.example.authservice.service;

import com.example.authservice.dto.SmsTypeDTO;
import com.example.authservice.model.SmsType;
import com.example.authservice.repo.SmsTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsTypeService {

    @Autowired
    private SmsTypeRepo smsTypeRepo;

    // Fetch all SMS types
    public List<SmsTypeDTO> getAllSmsTypes() {
        List<SmsType> smsTypes = smsTypeRepo.findAll();
        return smsTypes.stream()
                .map(smsType -> new SmsTypeDTO(smsType.getId(), smsType.getType(), smsType.getDescription()))
                .collect(Collectors.toList());
    }

    // Add a new SMS type
    public SmsTypeDTO addSmsType(SmsTypeDTO smsTypeDTO) {
        SmsType smsType = new SmsType();
        smsType.setType(smsTypeDTO.getType());
        smsType.setDescription(smsTypeDTO.getDescription());

        SmsType savedSmsType = smsTypeRepo.save(smsType);
        return new SmsTypeDTO(savedSmsType.getId(), savedSmsType.getType(), savedSmsType.getDescription());
    }

    // Fetch a single SMS type by ID
    public SmsTypeDTO getSmsTypeById(Long id) {
        SmsType smsType = smsTypeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("SmsType not found with ID: " + id));
        return new SmsTypeDTO(smsType.getId(), smsType.getType(), smsType.getDescription());
    }

    // Update an existing SMS type
    public SmsTypeDTO updateSmsType(Long id, SmsTypeDTO smsTypeDTO) {
        SmsType smsType = smsTypeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("SmsType not found with ID: " + id));

        smsType.setType(smsTypeDTO.getType());
        smsType.setDescription(smsTypeDTO.getDescription());

        SmsType updatedSmsType = smsTypeRepo.save(smsType);
        return new SmsTypeDTO(updatedSmsType.getId(), updatedSmsType.getType(), updatedSmsType.getDescription());
    }

    // Delete an SMS type
    public void deleteSmsType(Long id) {
        if (!smsTypeRepo.existsById(id)) {
            throw new RuntimeException("SmsType not found with ID: " + id);
        }
        smsTypeRepo.deleteById(id);
    }
}