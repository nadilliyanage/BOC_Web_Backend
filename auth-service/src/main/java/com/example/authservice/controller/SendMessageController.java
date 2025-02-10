package com.example.authservice.controller;

import com.example.authservice.dto.CreateMessageDTO;
import com.example.authservice.dto.SendMessageDTO;
import com.example.authservice.service.SendMessageService;
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
        return ResponseEntity.ok(sendMessageService.getScheduledMessages());
    }

    // Fetch pending finished
    @GetMapping("/finished")
    public ResponseEntity<List<SendMessageDTO>> getFinishedMessages() {
        return ResponseEntity.ok(sendMessageService.getFinishedMessages());
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
}