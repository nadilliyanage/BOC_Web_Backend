package com.example.authservice.repo;

import com.example.authservice.model.SendMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendMessageRepository extends JpaRepository<SendMessage, Long> {
}