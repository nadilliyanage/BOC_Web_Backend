package com.example.authservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SendMessageDTO {
    private String campaignName;
    private String sender;
    private List<String> numbers;
    private String message;
    private LocalDateTime schedule; // Local date and time for Sri Lanka

    private boolean removeBlockedNumbers;

    // Getters and Setters
    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDateTime schedule) {
        this.schedule = schedule;
    }

    // Getters and setters
    public boolean isRemoveBlockedNumbers() {
        return removeBlockedNumbers;
    }

    public void setRemoveBlockedNumbers(boolean removeBlockedNumbers) {
        this.removeBlockedNumbers = removeBlockedNumbers;
    }
}

