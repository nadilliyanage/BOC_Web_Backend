package com.example.authservice.controller;

import com.example.authservice.dto.SendMessageDTO;
import com.example.authservice.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/send-message")
public class SendMessageController {

    @Autowired
    private SendMessageService sendMessageService;

    @PostMapping
    public void saveSendMessage(@RequestBody SendMessageDTO sendMessageDTO) {
        sendMessageService.saveSendMessage(sendMessageDTO);
    }
}