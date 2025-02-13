package com.example.authservice.repo;

import com.example.authservice.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTableRepository extends JpaRepository<UserTable, String> {
    Optional<UserTable> findByUserId(String userId);
}