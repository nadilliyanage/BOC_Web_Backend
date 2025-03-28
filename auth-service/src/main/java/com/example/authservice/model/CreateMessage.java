package com.example.authservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessage {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private String message;
    private String created_by;
    private int created_by_id;
    private String created_by_userId;
    private String status_update_by;
    private int status_update_by_id;
    private String status_update_by_userId;
    private String updated_by;
    private int updated_by_id;
    private String updated_by_userId;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'pending'")
    private String status; // New field to track message status (e.g., pending, rejected, accepted)


}
