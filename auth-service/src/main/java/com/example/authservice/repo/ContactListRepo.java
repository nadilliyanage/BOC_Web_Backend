package com.example.authservice.repo;

import com.example.authservice.model.ContactList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContactListRepo extends JpaRepository<ContactList, Long> {

    // Find all contacts by file name
    List<ContactList> findByFileName(String fileName);

    // Delete all contacts associated with a file name
    void deleteByFileName(String fileName);

    // Delete a specific contact by contact number
    void deleteByNumber(String number);

    // Find a contact by contact number
    ContactList findByNumber(String number);
}
