package com.example.authservice.repo;

import com.example.authservice.model.NumberBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumberBlockRepo extends JpaRepository<NumberBlock, Long> {
    boolean existsByNumber(String number); // Check if a number is already blocked
}