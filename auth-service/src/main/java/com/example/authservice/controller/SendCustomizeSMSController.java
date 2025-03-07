package com.example.authservice.controller;

import com.example.authservice.dto.SendCustomizeSMSDTO;
import com.example.authservice.service.SendCustomizeSMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/send-customize-sms") // Updated endpoint
public class SendCustomizeSMSController {

    @Autowired
    private SendCustomizeSMSService sendCustomizeSMSService; // Updated service name

    @PostMapping
    public ResponseEntity<String> sendCustomizeSMS(@RequestBody SendCustomizeSMSDTO sendCustomizeSMSDTO) {
        try {
            // Convert schedule to Sri Lanka time if it is in UTC
            if (sendCustomizeSMSDTO.getSchedule() != null) {
                ZonedDateTime utcSchedule = ZonedDateTime.parse(sendCustomizeSMSDTO.getSchedule() + "Z");
                ZonedDateTime sriLankaSchedule = utcSchedule.withZoneSameInstant(ZoneId.of("Asia/Colombo"));
                sendCustomizeSMSDTO.setSchedule(sriLankaSchedule.toLocalDateTime());
            }

            // Save the message
            sendCustomizeSMSService.saveSendMessage(sendCustomizeSMSDTO);
            return ResponseEntity.ok("SMS campaign saved successfully! Blocked numbers were removed.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Return error message
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving SMS campaign: " + e.getMessage());
        }
    }

    // Other endpoints...
}