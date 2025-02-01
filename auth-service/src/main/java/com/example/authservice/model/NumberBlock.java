package com.example.authservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class NumberBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String number; // Blocked phone number
}