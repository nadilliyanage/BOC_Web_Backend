package com.example.authservice.service;

import com.example.authservice.dto.*;
import com.example.authservice.model.CreateMessage;
import com.example.authservice.model.SendMessage;
import com.example.authservice.repo.SendMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SendMessageService {

    private static final Logger logger = LoggerFactory.getLogger(SendMessageService.class);

    @Autowired
    private SendMessageRepository sendMessageRepository;

    @Autowired
    private NumberBlockService numberBlockService;

    private static final ZoneId SRI_LANKA_ZONE = ZoneId.of("Asia/Colombo");

    public void saveSendMessage(SendMessageDTO sendMessageDTO) {
        String campaignName = sendMessageDTO.getCampaignName();
        String sender = sendMessageDTO.getSender();
        String message = sendMessageDTO.getMessage();
        LocalDateTime schedule = sendMessageDTO.getSchedule();
        List<String> numbers = sendMessageDTO.getNumbers();
        boolean removeBlockedNumbers = sendMessageDTO.isRemoveBlockedNumbers(); // Get checkbox value
        String created_by = sendMessageDTO.getCreated_by();
        int created_by_id = sendMessageDTO.getCreated_by_id();
        String created_by_userId = sendMessageDTO.getCreated_by_userId();

        // Filter out blocked numbers if the checkbox is ticked
        List<String> validNumbers = numbers;
        if (removeBlockedNumbers) {
            validNumbers = numbers.stream()
                    .filter(number -> !numberBlockService.isNumberBlocked(number)) // Keep only non-blocked numbers
                    .collect(Collectors.toList());
        }

        // If no valid numbers are left, throw an exception
        if (validNumbers.isEmpty()) {
            throw new RuntimeException("No valid numbers to send.");
        }

        // Save each valid number as a separate record
        for (String number : validNumbers) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setCampaignName(campaignName);
            sendMessage.setSender(sender);
            sendMessage.setNumber(number);
            sendMessage.setMessage(message);
            sendMessage.setSchedule(schedule);
            sendMessage.setCreatedBy(created_by);
            sendMessage.setCreated_by_id(created_by_id);
            sendMessage.setCreated_by_userId(created_by_userId);

            // Set status based on schedule
            LocalDateTime now = LocalDateTime.now(SRI_LANKA_ZONE);
            if (schedule != null && schedule.isAfter(now)) {
                sendMessage.setStatus("Scheduled");
            } else {
                sendMessage.setStatus("Pending");
            }

            sendMessageRepository.save(sendMessage);
        }
    }

    // Scheduled task to update status from "Scheduled" to "Pending"
    @Scheduled(fixedRate = 60000)
    public void updateScheduledMessages() {
        LocalDateTime now = LocalDateTime.now(SRI_LANKA_ZONE);
        List<SendMessage> scheduledMessages = sendMessageRepository.findByStatusAndScheduleBefore("Scheduled", now);

        for (SendMessage message : scheduledMessages) {
            message.setStatus("Pending");
            sendMessageRepository.save(message);
        }
    }

    public List<SendMessageDTO> getAllSendMessage() {
        List<SendMessage> createMessages = sendMessageRepository.findAll();
        return createMessages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Fetch pending messages
    public List<SendMessageDTO> getPendingMessages() {
        List<SendMessage> pendingMessages = sendMessageRepository.findByStatus("Pending");
        return pendingMessages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<SendMessageDTO> getScheduledMessages() {
        logger.debug("Fetching scheduled messages from repository");
        try {
            List<SendMessage> scheduledMessages = sendMessageRepository.findByStatus("Scheduled");
            logger.debug("Found {} scheduled messages", scheduledMessages.size());
            return scheduledMessages.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error in getScheduledMessages: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<SendMessageDTO> getFinishedMessages() {
        logger.debug("Fetching finished messages from repository");
        try {
            List<SendMessage> finishedMessages = sendMessageRepository.findByReferenceNumberIsNotNull();
            logger.debug("Found {} finished messages", finishedMessages.size());
            return finishedMessages.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error in getFinishedMessages: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<SendMessageDTO> getErrorMessages() {
        logger.debug("Fetching error messages from repository");
        try {
            List<String> validStatuses = Arrays.asList("Pending", "Scheduled", "Finished");
            List<SendMessage> errorMessages = sendMessageRepository.findByStatusNotIn(validStatuses);
            logger.debug("Found {} error messages", errorMessages.size());
            return errorMessages.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error in getErrorMessages: {}", e.getMessage(), e);
            throw e;
        }
    }

    private SendMessageDTO mapToDTO(SendMessage sendMessage) {
        SendMessageDTO dto = new SendMessageDTO();
        dto.setCampaignName(sendMessage.getCampaignName());
        dto.setSender(sendMessage.getSender());
        dto.setNumbers(Collections.singletonList(sendMessage.getNumber()));
        dto.setMessage(sendMessage.getMessage());
        dto.setSchedule(sendMessage.getSchedule());
        dto.setStatus(sendMessage.getStatus());
        dto.setReferenceNumber(sendMessage.getReferenceNumber());
        dto.setCreated_at(sendMessage.getCreatedAt());
        dto.setCreated_by(sendMessage.getCreatedBy());
        dto.setCreated_by_id(sendMessage.getCreated_by_id());
        dto.setCreated_by_userId(sendMessage.getCreated_by_userId());
        return dto;
    }

    public long getCountOfPendingMessage() {
        return sendMessageRepository.findByStatus("Pending").size();
    }

    public long getCountOfScheduledMessage() {
        return sendMessageRepository.findByStatus("Scheduled").size();
    }

    public long getCountOfErrorMessage() {
        return sendMessageRepository.findByStatusNotIn(Arrays.asList("Pending", "Scheduled", "Finished")).size();
    }

    public List<MessageCountByDateDTO> getMessageCountByDate() {
        return sendMessageRepository.findMessageCountByDate();
    }

    public List<MessageCountByDateDTO> getMessageCountByDate(int year, int month) {
        return sendMessageRepository.findMessageCountByDate(year, month);
    }

    public List<MessageCountByMonthDTO> getMessageCountByMonth(int year) {
        return sendMessageRepository.findMessageCountByMonth(year);
    }

    public List<MessageCountByYearDTO> getMessageCountByYear() {
        return sendMessageRepository.findMessageCountByYear();
    }

    public SendMessage saveMessage(SendMessageDTO messageDTO) {
        SendMessage message = new SendMessage();
        message.setSender(messageDTO.getSender());
        message.setNumber(String.join(",", messageDTO.getNumbers())); // Convert List<String> to comma-separated string
        message.setMessage(messageDTO.getMessage());
        message.setStatus(messageDTO.getStatus());
        message.setCreatedBy(messageDTO.getCreated_by());
        message.setCreated_by_id(messageDTO.getCreated_by_id() != null ? messageDTO.getCreated_by_id() : 0);
        message.setCreated_by_userId(messageDTO.getCreated_by_userId());
        message.setCreatedAt(LocalDateTime.now());
        message.setSchedule(messageDTO.getSchedule());
        message.setCampaignName(messageDTO.getCampaignName());
        return sendMessageRepository.save(message);
    }
}