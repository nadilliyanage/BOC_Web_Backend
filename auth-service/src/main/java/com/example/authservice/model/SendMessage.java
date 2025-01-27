package com.example.authservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SendMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mobile;
    private String message;

    // Constructors
    public SendMessage() {
    }

    public SendMessage(String mobile, String message) {
        this.mobile = mobile;
        this.message = message;
    }
}

    // Getters and Setters

