package com.example.authservice.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SendMessageDTO {
    private String campaignName;
    private String sender;
    private List<String> numbers;
    private String message;
    private LocalDateTime schedule;
    private LocalDateTime created_at;
    private String status;
    private String referenceNumber;
    private boolean removeBlockedNumbers;
    private String created_by;
    private int created_by_id;
    private String creted_by_userId;


    public SendMessageDTO( String campaignName, String numbers, String message, String sender, LocalDateTime schedule, String status, String referenceNumber ,  LocalDateTime created_at , String created_by, int created_by_id, String creted_by_userId) {
        this.campaignName = campaignName;
        this.numbers = Collections.singletonList(numbers);
        this.message = message;
        this.sender = sender;
        this.schedule = schedule;
        this.status = status;
        this.referenceNumber = referenceNumber;
        this.created_at = created_at;
        this.created_by = created_by;
        this.created_by_id=created_by_id;
        this.creted_by_userId=creted_by_userId;


    }
}

