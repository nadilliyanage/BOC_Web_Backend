package com.example.authservice.service;

import com.example.authservice.dto.NumberBlockDTO;
import com.example.authservice.model.NumberBlock;
import com.example.authservice.repo.NumberBlockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NumberBlockService {

    @Autowired
    private NumberBlockRepo numberBlockRepository;

    // Add a single blocked number
    public NumberBlockDTO addBlockedNumber(NumberBlockDTO numberBlockDTO) {
        String number = numberBlockDTO.getNumber();

        // Validate number format
        if (!number.startsWith("94") || number.length() != 11) {
            throw new RuntimeException("Number must start with '94' and have a length of 11.");
        }

        // Check for duplicates
        if (numberBlockRepository.existsByNumber(number)) {
            throw new RuntimeException("Number already blocked: " + number);
        }

        // Save the number
        NumberBlock numberBlock = new NumberBlock();
        numberBlock.setNumber(number);
        numberBlockRepository.save(numberBlock);

        return new NumberBlockDTO(numberBlock.getId(), numberBlock.getNumber());
    }

    // Add multiple blocked numbers from a CSV file
    public List<NumberBlockDTO> addBlockedNumbersFromCSV(List<String> numbers) {
        return numbers.stream()
                .filter(number -> number.startsWith("94") && number.length() == 11) // Validate number format
                .filter(number -> !numberBlockRepository.existsByNumber(number)) // Check for duplicates
                .map(number -> {
                    NumberBlock numberBlock = new NumberBlock();
                    numberBlock.setNumber(number);
                    numberBlockRepository.save(numberBlock);
                    return new NumberBlockDTO(numberBlock.getId(), numberBlock.getNumber());
                })
                .collect(Collectors.toList());
    }

    // Get all blocked numbers
    public List<NumberBlockDTO> getAllBlockedNumbers() {
        return numberBlockRepository.findAll().stream()
                .map(numberBlock -> new NumberBlockDTO(numberBlock.getId(), numberBlock.getNumber()))
                .collect(Collectors.toList());
    }

    // Delete a blocked number by ID
    public void deleteBlockedNumber(Long id) {
        numberBlockRepository.deleteById(id);
    }

    public NumberBlockDTO updateBlockedNumber(Long id, NumberBlockDTO numberBlockDTO) {
        String newNumber = numberBlockDTO.getNumber();

        // Validate number format
        if (!newNumber.startsWith("94") || newNumber.length() != 11) {
            throw new RuntimeException("Number must start with '94' and have a length of 11.");
        }

        // Check if the new number already exists
        if (numberBlockRepository.existsByNumber(newNumber)) {
            throw new RuntimeException("Number already blocked: " + newNumber);
        }

        // Find the existing number block
        NumberBlock numberBlock = numberBlockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Number not found with ID: " + id));

        // Update the number
        numberBlock.setNumber(newNumber);
        numberBlockRepository.save(numberBlock);

        return new NumberBlockDTO(numberBlock.getId(), numberBlock.getNumber());
    }

}