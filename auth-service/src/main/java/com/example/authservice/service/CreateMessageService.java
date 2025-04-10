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

    // Fetch all messages
    public List<CreateMessageDTO> getAllCreateMessage() {
        List<CreateMessage> createMessages = createMessageRepo.findAll();
        return createMessages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Fetch pending messages
    public List<CreateMessageDTO> getPendingMessages() {
        List<CreateMessage> pendingMessages = createMessageRepo.findByStatus("pending");
        return pendingMessages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Fetch rejected messages
    public List<CreateMessageDTO> getRejectedMessages() {
        List<CreateMessage> rejectedMessages = createMessageRepo.findByStatus("rejected");
        return rejectedMessages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Fetch accepted messages
    public List<CreateMessageDTO> getAcceptedMessages() {
        List<CreateMessage> acceptedMessages = createMessageRepo.findByStatus("accepted");
        return acceptedMessages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Add a new message
    public CreateMessageDTO addCreateMessage(CreateMessageDTO createMessageDTO) {
        CreateMessage createMessage = new CreateMessage();
        createMessage.setLabel(createMessageDTO.getLabel());
        createMessage.setMessage(createMessageDTO.getMessage());
        createMessage.setStatus("pending"); // Default status is "pending"
        createMessage.setCreated_by(createMessageDTO.getCreated_by());
        createMessage.setCreated_by_id(createMessageDTO.getCreated_by_id());
        createMessage.setCreated_by_userId(createMessageDTO.getCreated_by_userId());

        CreateMessage savedCreateMessage = createMessageRepo.save(createMessage);
        return mapToDTO(savedCreateMessage);
    }

    // Fetch a single message by ID
    public CreateMessageDTO getCreateMessageById(Long id) {
        CreateMessage createMessage = createMessageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CreateMessage not found with ID: " + id));
        return mapToDTO(createMessage);
    }

    // Update an existing message
    public CreateMessageDTO updateCreateMessage(Long id, CreateMessageDTO createMessageDTO) {
        CreateMessage createMessage = createMessageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CreateMessage not found with ID: " + id));

        createMessage.setLabel(createMessageDTO.getLabel());
        createMessage.setMessage(createMessageDTO.getMessage());
        createMessage.setStatus("pending"); // Allow status updates
        createMessage.setUpdated_by(createMessageDTO.getUpdated_by());
        createMessage.setUpdated_by_id(createMessage.getUpdated_by_id());
        createMessage.setUpdated_by_userId(createMessageDTO.getUpdated_by_userId());

        CreateMessage updatedCreateMessage = createMessageRepo.save(createMessage);
        return mapToDTO(updatedCreateMessage);
    }

    // Update message status (accept/reject)
    public CreateMessageDTO updateMessageStatus(Long id, CreateMessageDTO createMessageDTO) {
        CreateMessage createMessage = createMessageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("CreateMessage not found with ID: " + id));

        createMessage.setStatus(createMessageDTO.getStatus()); // Allow status updates
        createMessage.setStatus_update_by(createMessageDTO.getStatus_update_by());
        createMessage.setStatus_update_by_id(createMessage.getStatus_update_by_id());
        createMessage.setStatus_update_by_userId(createMessageDTO.getStatus_update_by_userId());

        CreateMessage updatedCreateMessage = createMessageRepo.save(createMessage);
        return mapToDTO(updatedCreateMessage);
    }




    // Delete a message
    public void deleteCreateMessage(Long id) {
        if (!createMessageRepo.existsById(id)) {
            throw new RuntimeException("CreateMessage not found with ID: " + id);
        }
        createMessageRepo.deleteById(id);
    }

    // Helper method: Map CreateMessage entity to DTO
    private CreateMessageDTO mapToDTO(CreateMessage createMessage) {
        return new CreateMessageDTO(
                createMessage.getId(),
                createMessage.getLabel(),
                createMessage.getMessage(),
                createMessage.getStatus(),
                createMessage.getCreated_by(),
                createMessage.getCreated_by_id(),
                createMessage.getCreated_by_userId(),
                createMessage.getStatus_update_by(),
                createMessage.getStatus_update_by_id(),
                createMessage.getStatus_update_by_userId(),
                createMessage.getUpdated_by(),
                createMessage.getUpdated_by_id(),
                createMessage.getUpdated_by_userId()


        );
    }

    public long getCountOfToReviewMessage() {
        return createMessageRepo.findByStatus("pending").size();
    }

}
