package com.example.authservice.controller;

import com.example.authservice.dto.CreateMessageDTO;
import com.example.authservice.service.CreateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/create-message")
public class CreateMessageController {

    @Autowired
    private CreateMessageService createMessage;

    // Fetch all SMS types
    @GetMapping
    public ResponseEntity<List<CreateMessageDTO>> getAllCreateMessage() {
        return ResponseEntity.ok(createMessage.getAllCreateMessage());
    }

    // Fetch pending messages
    @GetMapping("/pending")
    public ResponseEntity<List<CreateMessageDTO>> getPendingMessages() {
        return ResponseEntity.ok(createMessage.getPendingMessages());
    }

    // Fetch rejected messages
    @GetMapping("/rejected")
    public ResponseEntity<List<CreateMessageDTO>> getRejectedMessages() {
        return ResponseEntity.ok(createMessage.getRejectedMessages());
    }

    // Fetch accepted messages
    @GetMapping("/accepted")
    public ResponseEntity<List<CreateMessageDTO>> getAcceptedMessages() {
        return ResponseEntity.ok(createMessage.getAcceptedMessages());
    }

    // Add a new SMS type
    @PostMapping
    public ResponseEntity<CreateMessageDTO> addCreateMessage(@RequestBody CreateMessageDTO createMessageDTO) {
        CreateMessageDTO createdCreateMessage = createMessage.addCreateMessage(createMessageDTO);
        return ResponseEntity.ok(createdCreateMessage);
    }

    // Fetch a single SMS type by ID
    @GetMapping("/{id}")
    public ResponseEntity<CreateMessageDTO> getCreateMessageById(@PathVariable Long id) {
        CreateMessageDTO createMessageDTO = createMessage.getCreateMessageById(id);
        return ResponseEntity.ok(createMessageDTO);
    }

    // Update an SMS type
    @PutMapping("/{id}")
    public ResponseEntity<CreateMessageDTO> updateCreateMessage(@PathVariable Long id, @RequestBody CreateMessageDTO createMessageDTO) {
        CreateMessageDTO updatedCreateMessage = createMessage.updateCreateMessage(id, createMessageDTO);
        return ResponseEntity.ok(updatedCreateMessage);
    }

    // Delete an SMS type
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreateMessage(@PathVariable Long id) {
        createMessage.deleteCreateMessage(id);
        return ResponseEntity.noContent().build();
    }

    // Update message status (accepted or rejected)
    @PutMapping("/status/{id}")
    public ResponseEntity<CreateMessageDTO> updateMessageStatus(@PathVariable Long id, @RequestBody CreateMessageDTO createMessageDTO) {
        CreateMessageDTO updatedMessageStatus = createMessage.updateMessageStatus(id, createMessageDTO);
        return ResponseEntity.ok(updatedMessageStatus);
    }


}
