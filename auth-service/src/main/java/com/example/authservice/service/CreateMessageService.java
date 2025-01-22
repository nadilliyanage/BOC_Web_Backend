package com.example.authservice.service;

import com.example.authservice.dto.CreateMessageDTO;
import com.example.authservice.model.CreateMessage;
import com.example.authservice.repo.CreateMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateMessageService {
    @Autowired
    private CreateMessageRepo createMessageRepo;

    // Fetch all SMS types
    public List<CreateMessageDTO> getAllCreateMessage() {
        List<CreateMessage> createMessage = createMessageRepo.findAll();
        return createMessage.stream()
                .map(createmsg -> new CreateMessageDTO(createmsg.getId(), createmsg.getLabel(), createmsg.getMessage()))
                .collect(Collectors.toList());
    }

    // Add a new SMS type
    public CreateMessageDTO addCreateMessage(CreateMessageDTO createmsgDTO) {
        CreateMessage createmsg = new CreateMessage();
        createmsg.setLabel(createmsgDTO.getLabel());
        createmsg.setMessage(createmsgDTO.getMessage());

        CreateMessage savedCreateMessage = createMessageRepo.save(createmsg);
        return new CreateMessageDTO(savedCreateMessage.getId(), savedCreateMessage.getLabel(), savedCreateMessage.getMessage());
    }

    // Fetch a single SMS type by ID
    public CreateMessageDTO getCreateMessageById(Long id) {
        CreateMessage createmsg = createMessageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CreateMessage not found with ID: " + id));
        return new CreateMessageDTO(createmsg.getId(), createmsg.getLabel(), createmsg.getMessage());
    }

    // Update an existing SMS type
    public CreateMessageDTO updateCreateMessage(Long id, CreateMessageDTO createmsgDTO) {
        CreateMessage createmsg = createMessageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CreateMessage not found with ID: " + id));

        createmsg.setLabel(createmsgDTO.getLabel());
        createmsg.setMessage(createmsgDTO.getMessage());

        CreateMessage updatedCreateMessage = createMessageRepo.save(createmsg);
        return new CreateMessageDTO(updatedCreateMessage.getId(), updatedCreateMessage.getLabel(), updatedCreateMessage.getMessage());
    }

    // Delete an SMS type
    public void deleteCreateMessage(Long id) {
        if (!createMessageRepo.existsById(id)) {
            throw new RuntimeException("CreateMessage not found with ID: " + id);
        }
        createMessageRepo.deleteById(id);
    }


}
