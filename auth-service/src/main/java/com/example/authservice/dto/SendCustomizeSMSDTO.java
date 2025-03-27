package com.example.authservice.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendCustomizeSMSDTO {
    private String campaignName; // Name of the SMS campaign
    private String sender; // Sender's name or ID
    private String number; // Recipient's phone number
    private String message; // Generated message (e.g., "Hello Bob, your number is 078546985")
    private LocalDateTime schedule; // Scheduled time for sending the message
    private boolean removeBlockedNumbers; // Flag to indicate whether to remove blocked numbers
    private String created_by;
    private int created_by_id;
    private String creted_by_userId;
}