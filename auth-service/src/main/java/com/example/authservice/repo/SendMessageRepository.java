package com.example.authservice.repo;

import com.example.authservice.model.SendMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SendMessageRepository extends JpaRepository<SendMessage, Long> {
    List<SendMessage> findByStatusAndScheduleBefore(String status, LocalDateTime schedule);
} 