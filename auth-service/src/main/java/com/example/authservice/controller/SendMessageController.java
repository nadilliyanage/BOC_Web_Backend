package com.example.authservice.controller;

import com.example.authservice.dto.SendMessageDTO;
import com.example.authservice.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.LocalDateTime;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/send-message")
public class SendMessageController {

    @Autowired
    private SendMessageService sendMessageService;

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
}