package com.example.authservice.service;

import com.example.authservice.dto.SendCustomizeSMSDTO;
import com.example.authservice.model.SendMessage;
import com.example.authservice.repo.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class SendCustomizeSMSService { // Updated service name

    @Autowired
    private SendMessageRepository sendMessageRepository;

//    @Autowired
//    private NumberBlockService numberBlockService;

    private static final ZoneId SRI_LANKA_ZONE = ZoneId.of("Asia/Colombo");

    public void saveSendMessage(SendCustomizeSMSDTO sendCustomizeSMSDTO) {
        String campaignName = sendCustomizeSMSDTO.getCampaignName();
        String sender = sendCustomizeSMSDTO.getSender();
        String number = sendCustomizeSMSDTO.getNumber(); // Recipient's number
        String message = sendCustomizeSMSDTO.getMessage(); // Generated message (e.g., "Hello Bob, your number is 078546985")
        LocalDateTime schedule = sendCustomizeSMSDTO.getSchedule();
        boolean removeBlockedNumbers = sendCustomizeSMSDTO.isRemoveBlockedNumbers();

//        // Check if the number is blocked (if applicable)
//        if (removeBlockedNumbers && numberBlockService.isNumberBlocked(number)) {
//            throw new RuntimeException("Number is blocked: " + number);
//        }

        // Save the message as a single record
        SendMessage sendMessage = new SendMessage();
        sendMessage.setCampaignName(campaignName);
        sendMessage.setSender(sender);
        sendMessage.setNumber(number);
        sendMessage.setMessage(message); // Save the generated message
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

    // Other methods...
}