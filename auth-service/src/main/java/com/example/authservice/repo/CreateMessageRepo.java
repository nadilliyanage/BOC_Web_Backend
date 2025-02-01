package com.example.authservice.repo;

import com.example.authservice.model.CreateMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreateMessageRepo extends JpaRepository<CreateMessage, Long> {
    // Find messages by status
    List<CreateMessage> findByStatus(String status);
}
