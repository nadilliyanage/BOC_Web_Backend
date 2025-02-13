package com.example.authservice.repo;

import com.example.authservice.model.ADTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ADTableRepository extends JpaRepository<ADTable, String> {
    Optional<ADTable> findByUserId(String userId);
}
