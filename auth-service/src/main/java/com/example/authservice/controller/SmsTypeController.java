package com.example.authservice.controller;

import com.example.authservice.dto.SmsTypeDTO;
import com.example.authservice.service.SmsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/sms-types")
public class SmsTypeController {

    @Autowired
    private SmsTypeService smsTypeService;

    // Fetch all SMS types
    @GetMapping
    public ResponseEntity<List<SmsTypeDTO>> getAllSmsTypes() {
        return ResponseEntity.ok(smsTypeService.getAllSmsTypes());
    }

    // Add a new SMS type
    @PostMapping
    public ResponseEntity<SmsTypeDTO> addSmsType(@RequestBody SmsTypeDTO smsTypeDTO) {
        SmsTypeDTO createdSmsType = smsTypeService.addSmsType(smsTypeDTO);
        return ResponseEntity.ok(createdSmsType);
    }

    // Fetch a single SMS type by ID
    @GetMapping("/{id}")
    public ResponseEntity<SmsTypeDTO> getSmsTypeById(@PathVariable Long id) {
        SmsTypeDTO smsTypeDTO = smsTypeService.getSmsTypeById(id);
        return ResponseEntity.ok(smsTypeDTO);
    }

    // Update an SMS type
    @PutMapping("/{id}")
    public ResponseEntity<SmsTypeDTO> updateSmsType(@PathVariable Long id, @RequestBody SmsTypeDTO smsTypeDTO) {
        SmsTypeDTO updatedSmsType = smsTypeService.updateSmsType(id, smsTypeDTO);
        return ResponseEntity.ok(updatedSmsType);
    }

    // Delete an SMS type
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSmsType(@PathVariable Long id) {
        smsTypeService.deleteSmsType(id);
        return ResponseEntity.noContent().build();
    }
}
