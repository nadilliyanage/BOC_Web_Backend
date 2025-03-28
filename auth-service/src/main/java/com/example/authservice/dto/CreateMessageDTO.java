package com.example.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageDTO {
    private Long id;
    private String label;
    private String message;
    private String status;
    private String created_by;
    private int created_by_id;
    private String created_by_userId;
    private String status_update_by;
    private int status_update_by_id;
    private String status_update_by_userId;
    private String updated_by;
    private int updated_by_id;
    private String updated_by_userId;

}
