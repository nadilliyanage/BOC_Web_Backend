package com.example.authservice.dto;


import lombok.NoArgsConstructor;



@NoArgsConstructor
public class MessageCountByMonthDTO {
    private int month; // Represents the month (1 for January, 2 for February, etc.)
    private long count; // Represents the count of messages

    // Constructor
    public MessageCountByMonthDTO(int month, long count) {
        this.month = month;
        this.count = count;
    }

    // Getters and Setters
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}