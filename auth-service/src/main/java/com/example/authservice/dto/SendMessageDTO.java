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
    private String status;
    private String refno;
    private boolean removeBlockedNumbers;


    public SendMessageDTO( String campaignName, String numbers, String message, String sender, LocalDateTime schedule, String status, String refno) {
        this.campaignName = campaignName;
        this.numbers = Collections.singletonList(numbers);
        this.message = message;
        this.sender = sender;
        this.schedule = schedule;
        this.status = status;
        this.refno = refno;

    }
}

