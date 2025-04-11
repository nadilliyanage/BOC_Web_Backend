package com.example.authservice.repo;

import com.example.authservice.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserTable, Integer> {

    // Custom query to get user by ID
    @Query(value = "SELECT * FROM user_table WHERE id = ?1", nativeQuery = true)
    UserTable getUserById(int userId);

    // Custom query method to check if a user exists by userId
    boolean existsByUserId(String userId);
}
