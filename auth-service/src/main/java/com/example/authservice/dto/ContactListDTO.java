package com.example.authservice.dto;

public class ContactListDTO {

    private String number;

    public ContactListDTO(String number) {
        this.number = number;
    }

    // Getters and Setters
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
