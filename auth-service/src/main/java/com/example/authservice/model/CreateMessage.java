package com.example.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class CreateMessage {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private String message;
    private String createdBy;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'pending'")
    private String status; // New field to track message status (e.g., pending, rejected, accepted)

    // Constructors
    public CreateMessage() {}

    public CreateMessage(String label, String message, String status) {
        this.label = label;
        this.message = message;
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "CreateMessage{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", message='" + message + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
