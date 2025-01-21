package com.example.authservice.repo;

import com.example.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    // Custom query to get user by ID
    @Query(value = "SELECT * FROM User WHERE id = ?1", nativeQuery = true)
    User getUserById(int userId);

    // Custom query method to check if a user exists by userId
    boolean existsByUserId(String userId);
}
