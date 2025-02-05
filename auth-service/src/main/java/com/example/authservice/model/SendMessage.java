package com.example.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Entity
public class SendMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String campaignName;
    private String sender;
    private String number;
    private String message;
    private LocalDateTime schedule; // Local date and time for Sri Lanka
    private String status;

    @CreationTimestamp
    private LocalDateTime createdAt; // Automatically populated with the current local date and time

    // Getters and Setters

    public void setId(Long id) {
        this.id = id;
    }



    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }



    public void setSender(String sender) {
        this.sender = sender;
    }



    public void setNumber(String number) {
        this.number = number;
    }



    public void setMessage(String message) {
        this.message = message;
    }


    public void setSchedule(LocalDateTime schedule) {
        this.schedule = schedule;
    }



    public void setStatus(String status) {
        this.status = status;
    }



    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}