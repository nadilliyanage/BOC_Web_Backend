package com.example.authservice.service;

import com.example.authservice.dto.*;
import com.example.authservice.model.CreateMessage;
import com.example.authservice.model.SendMessage;
import com.example.authservice.repo.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SendMessageService {

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

        // Filter out blocked numbers if the checkbox is ticked
        List<String> validNumbers = numbers;
        if (removeBlockedNumbers) {
            validNumbers = numbers.stream()
                    .filter(number -> !numberBlockService.isNumberBlocked(number)) // Keep only non-blocked numbers
                    .collect(Collectors.toList());
        }

        // If no valid numbers are left, throw an exception
        if (validNumbers.isEmpty()) {
            throw new RuntimeException("No valid numbers to save.");
        }

        // Save each valid number as a separate record
        for (String number : validNumbers) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setCampaignName(campaignName);
            sendMessage.setSender(sender);
            sendMessage.setNumber(number);
            sendMessage.setMessage(message);
            sendMessage.setSchedule(schedule);

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
        List<SendMessage> scheduledMessages = sendMessageRepository.findByStatus("Scheduled");
        return scheduledMessages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public List<SendMessageDTO> getFinishedMessages() {
        List<SendMessage> finishedMessages = sendMessageRepository.findByReferenceNumberIsNotNull();
        return finishedMessages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    public List<SendMessageDTO> getErrorMessages() {
        List<SendMessage> errorMessages = sendMessageRepository.findByStatusNotIn
                (Arrays.asList("Pending", "Scheduled", "Finished"));;
        return errorMessages.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    private SendMessageDTO mapToDTO(SendMessage sendMessage) {
        return new SendMessageDTO(
                sendMessage.getCampaignName(),
                sendMessage.getNumber(),
                sendMessage.getMessage(),
                sendMessage.getSender(),
                sendMessage.getSchedule(),
                sendMessage.getStatus(),
                sendMessage.getReferenceNumber()
        );
    }

    public long getCountOfPendingMessage() {
        return sendMessageRepository.findByStatus("Pending").size();
    }

    public long getCountOfScheduledMessage() {
        return sendMessageRepository.findByStatus("Scheduled").size();
    }

    public long getCountOfErrorMessage() {
        return sendMessageRepository.findByStatus("INVALID_MOBILE").size();
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
}