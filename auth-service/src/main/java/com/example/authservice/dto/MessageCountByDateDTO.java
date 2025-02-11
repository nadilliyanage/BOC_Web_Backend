package com.example.authservice.dto;

import java.util.Date;

public class MessageCountByDateDTO {
    private Date date;
    private long count;

    public MessageCountByDateDTO(Date date, long count) {
        this.date = date;
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
