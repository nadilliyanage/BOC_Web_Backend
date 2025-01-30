package com.example.authservice.service;



import com.example.authservice.dto.SendMessageDTO;
import com.example.authservice.model.SendMessage;
import com.example.authservice.repo.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SendMessageService {

    @Autowired
    private SendMessageRepository sendMessageRepository;

    public void saveSendMessage(SendMessageDTO sendMessageDTO) {
        String campaignName = sendMessageDTO.getCampaignName();
        String sender = sendMessageDTO.getSender();
        String message = sendMessageDTO.getMessage();
        LocalDateTime schedule = sendMessageDTO.getSchedule();
        List<String> numbers = sendMessageDTO.getNumbers();

        // Save each number as a separate record
        for (String number : numbers) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setCampaignName(campaignName);
            sendMessage.setSender(sender);
            sendMessage.setNumber(number);
            sendMessage.setMessage(message);
            sendMessage.setSchedule(schedule); // Save the schedule as-is

            // Set status based on schedule
            if (schedule != null && schedule.isAfter(LocalDateTime.now())) {
                sendMessage.setStatus("Scheduled");
            } else {
                sendMessage.setStatus("Pending");
            }

            sendMessageRepository.save(sendMessage);
        }
    }
}