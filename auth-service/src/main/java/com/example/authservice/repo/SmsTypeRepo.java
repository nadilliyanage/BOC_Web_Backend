package com.example.authservice.repo;

import com.example.authservice.model.SmsType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsTypeRepo extends JpaRepository<SmsType, Long> {

}
