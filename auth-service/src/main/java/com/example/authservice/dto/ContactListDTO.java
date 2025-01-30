package com.example.authservice.dto;

public class ContactListDTO {
    private Long id; // Add ID field
    private String number;

    public ContactListDTO(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}