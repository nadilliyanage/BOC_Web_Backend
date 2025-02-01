package com.example.authservice.controller;

import com.example.authservice.dto.NumberBlockDTO;
import com.example.authservice.service.NumberBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/number-block")
public class NumberBlockController {

    @Autowired
    private NumberBlockService numberBlockService;

    // Add a single blocked number
    @PostMapping
    public ResponseEntity<?> addBlockedNumber(@RequestBody NumberBlockDTO numberBlockDTO) {
        try {
            return ResponseEntity.ok(numberBlockService.addBlockedNumber(numberBlockDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Add multiple blocked numbers from a CSV file
    @PostMapping("/upload-csv")
    public ResponseEntity<?> addBlockedNumbersFromCSV(@RequestParam("file") MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<String> numbers = reader.lines().collect(Collectors.toList());
            return ResponseEntity.ok(numberBlockService.addBlockedNumbersFromCSV(numbers));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get all blocked numbers
    @GetMapping
    public ResponseEntity<List<NumberBlockDTO>> getAllBlockedNumbers() {
        return ResponseEntity.ok(numberBlockService.getAllBlockedNumbers());
    }

    // Delete a blocked number by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlockedNumber(@PathVariable Long id) {
        numberBlockService.deleteBlockedNumber(id);
        return ResponseEntity.noContent().build();
    }

    // Update a blocked number
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlockedNumber(@PathVariable Long id, @RequestBody NumberBlockDTO numberBlockDTO) {
        try {
            return ResponseEntity.ok(numberBlockService.updateBlockedNumber(id, numberBlockDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}