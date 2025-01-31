package com.example.authservice.service;

import com.example.authservice.dto.SendMessageDTO;
import com.example.authservice.model.SendMessage;
import com.example.authservice.repo.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class SendMessageService {

    @Autowired
    private SendMessageRepository sendMessageRepository;

    // Sri Lanka timezone
    private static final ZoneId SRI_LANKA_ZONE = ZoneId.of("Asia/Colombo");

    public void saveSendMessage(SendMessageDTO sendMessageDTO) {
        String campaignName = sendMessageDTO.getCampaignName();
        String sender = sendMessageDTO.getSender();
        String message = sendMessageDTO.getMessage();
        LocalDateTime schedule = sendMessageDTO.getSchedule(); // Assume schedule is in local time (Sri Lanka)
        List<String> numbers = sendMessageDTO.getNumbers();

        // Save each number as a separate record
        for (String number : numbers) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setCampaignName(campaignName);
            sendMessage.setSender(sender);
            sendMessage.setNumber(number);
            sendMessage.setMessage(message);
            sendMessage.setSchedule(schedule);

            // Set status based on schedule
            LocalDateTime now = LocalDateTime.now(SRI_LANKA_ZONE); // Current time in Sri Lanka
            if (schedule != null && schedule.isAfter(now)) {
                sendMessage.setStatus("Scheduled");
            } else {
                sendMessage.setStatus("Pending");
            }

            sendMessageRepository.save(sendMessage);
        }
    }

    // Scheduled task to update status from "Scheduled" to "Pending" when the schedule time has passed
    @Scheduled(fixedRate = 60000) // Runs every minute (adjust as needed)
    public void updateScheduledMessages() {
        LocalDateTime now = LocalDateTime.now(SRI_LANKA_ZONE); // Current time in Sri Lanka
        List<SendMessage> scheduledMessages = sendMessageRepository.findByStatusAndScheduleBefore("Scheduled", now);

        for (SendMessage message : scheduledMessages) {
            message.setStatus("Pending");
            sendMessageRepository.save(message);
        }
    }
}