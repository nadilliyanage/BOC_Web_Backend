package com.example.authservice.repo;

import com.example.authservice.model.ContactList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactListRepo extends JpaRepository<ContactList, Long> {
    List<ContactList> findByFileName(String fileName);
}

