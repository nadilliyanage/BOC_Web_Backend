package com.example.authservice.controller;

import com.example.authservice.dto.*;
import com.example.authservice.service.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/send-message")
public class SendMessageController {

    private static final Logger logger = LoggerFactory.getLogger(SendMessageController.class);

    @Autowired
    private SendMessageService sendMessageService;

    @GetMapping
    public ResponseEntity<List<SendMessageDTO>> getAllSendMessage() {
        return ResponseEntity.ok(sendMessageService.getAllSendMessage());
    }

    // Fetch pending messages
    @GetMapping("/pending")
    public ResponseEntity<List<SendMessageDTO>> getPendingMessages() {
        return ResponseEntity.ok(sendMessageService.getPendingMessages());
    }

    // Fetch pending scheduled
    @GetMapping("/scheduled")
    public ResponseEntity<List<SendMessageDTO>> getScheduledMessages() {
        try {
            logger.info("Fetching scheduled messages");
            List<SendMessageDTO> scheduledMessages = sendMessageService.getScheduledMessages();
            logger.info("Found {} scheduled messages", scheduledMessages.size());
            return ResponseEntity.ok(scheduledMessages);
        } catch (Exception e) {
            logger.error("Error fetching scheduled messages: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    // Fetch pending finished
    @GetMapping("/finished")
    public ResponseEntity<List<SendMessageDTO>> getFinishedMessages() {
        try {
            logger.info("Fetching finished messages");
            List<SendMessageDTO> finishedMessages = sendMessageService.getFinishedMessages();
            logger.info("Found {} finished messages", finishedMessages.size());
            return ResponseEntity.ok(finishedMessages);
        } catch (Exception e) {
            logger.error("Error fetching finished messages: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/error")
    public ResponseEntity<List<SendMessageDTO>> getErrorMessages() {
        try {
            logger.info("Fetching error messages");
            List<SendMessageDTO> errorMessages = sendMessageService.getErrorMessages();
            logger.info("Found {} error messages", errorMessages.size());
            return ResponseEntity.ok(errorMessages);
        } catch (Exception e) {
            logger.error("Error fetching error messages: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody SendMessageDTO sendMessageDTO) {
        try {
            // Convert schedule to Sri Lanka time if it is in UTC
            if (sendMessageDTO.getSchedule() != null) {
                ZonedDateTime utcSchedule = ZonedDateTime.parse(sendMessageDTO.getSchedule() + "Z");
                ZonedDateTime sriLankaSchedule = utcSchedule.withZoneSameInstant(ZoneId.of("Asia/Colombo"));
                sendMessageDTO.setSchedule(sriLankaSchedule.toLocalDateTime());
            }

            // Save the message
            sendMessageService.saveSendMessage(sendMessageDTO);
            return ResponseEntity.ok("SMS campaign saved successfully! Blocked numbers were removed.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Return error message
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving SMS campaign: " + e.getMessage());
        }
    }

    // Endpoint to get the count of pending SMS
    @GetMapping("/pending-sms-count")
    public ResponseEntity<Long> getCountOfPendingMessage() {
        long pendingSMSCount = sendMessageService.getCountOfPendingMessage();
        return ResponseEntity.ok(pendingSMSCount);
    }

    // Endpoint to get the count of scheduled SMS
    @GetMapping("/scheduled-sms-count")
    public ResponseEntity<Long> getCountOfScheduledMessage() {
        long scheduledSMSCount = sendMessageService.getCountOfScheduledMessage();
        return ResponseEntity.ok(scheduledSMSCount);
    }

    @GetMapping("/error-sms-count")
    public ResponseEntity<Long> getCountOfErrorMessage() {
        long errorSMSCount = sendMessageService.getCountOfErrorMessage();
        return ResponseEntity.ok(errorSMSCount);
    }

    @GetMapping("/message-count-by-date")
    public ResponseEntity<List<MessageCountByDateDTO>> getMessageCountByDate(
            @RequestParam int year,
            @RequestParam int month) {
        List<MessageCountByDateDTO> messageCountByDate = sendMessageService.getMessageCountByDate(year, month);
        return ResponseEntity.ok(messageCountByDate);
    }

    @GetMapping("/message-count-by-month")
    public ResponseEntity<List<MessageCountByMonthDTO>> getMessageCountByMonth(@RequestParam int year) {
        List<MessageCountByMonthDTO> messageCountByMonth = sendMessageService.getMessageCountByMonth(year);
        return ResponseEntity.ok(messageCountByMonth);
    }

    @GetMapping("/message-count-by-year")
    public ResponseEntity<List<MessageCountByYearDTO>> getMessageCountByYear() {
        List<MessageCountByYearDTO> messageCountByYear = sendMessageService.getMessageCountByYear();
        return ResponseEntity.ok(messageCountByYear);
    }
}