package com.example.authservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
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
    private String referenceNumber;
    private String createdBy;
    private Integer created_by_id;
    private String created_by_userId;

    @CreationTimestamp
    private LocalDateTime createdAt; // Automatically populated with the current local date and time

}