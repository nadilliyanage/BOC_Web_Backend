package com.example.authservice.controller;

import com.example.authservice.dto.ContactListDTO;
import com.example.authservice.service.ContactListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/contact-list")
public class ContactListController {

    @Autowired
    private ContactListService contactListService;

    // Endpoint to handle file upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            contactListService.uploadCSV(file);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading file.");
        }
    }

    // Endpoint to get numbers by file name
    @GetMapping("/findByFileName")
    public ResponseEntity<List<ContactListDTO>> findByFileName(@RequestParam String fileName) {
        List<ContactListDTO> contacts = contactListService.findByFileName(fileName);
        return ResponseEntity.ok(contacts);
    }

    // Endpoint to fetch list of uploaded files
    @GetMapping("/files")
    public ResponseEntity<List<String>> getUploadedFiles() {
        List<String> fileNames = contactListService.getUploadedFiles();
        return ResponseEntity.ok(fileNames);
    }

    // Endpoint to delete file and associated records
    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        try {
            contactListService.deleteFile(fileName);
            return ResponseEntity.ok("File and associated records deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting file.");
        }
    }

    // Endpoint to edit a contact's number by user ID
    @PutMapping("/editNumber/{id}")
    public ResponseEntity<String> editContactNumber(@PathVariable Long id, @RequestParam String newNumber) {
        try {
            contactListService.editContactNumber(id, newNumber);
            return ResponseEntity.ok("Contact number updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error updating contact number: " + e.getMessage());
        }
    }


}
