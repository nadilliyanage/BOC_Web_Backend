package com.example.authservice.service;

import com.example.authservice.dto.ContactListDTO;
import com.example.authservice.model.ContactList;
import com.example.authservice.repo.ContactListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactListService {

    @Autowired
    private ContactListRepo contactListRepository;

    // Fetch contacts by file name and include their IDs
    public List<ContactListDTO> findByFileName(String fileName) {
        List<ContactList> contactList = contactListRepository.findByFileName(fileName);
        return contactList.stream()
                .map(contact -> new ContactListDTO(contact.getId(), contact.getNumber())) // Include ID
                .collect(Collectors.toList());
    }

    // Upload CSV file and save contacts
    public void uploadCSV(MultipartFile file) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            ContactList contact = new ContactList();
            contact.setNumber(line.trim());
            contact.setFileName(file.getOriginalFilename());
            contactListRepository.save(contact);
        }
    }

    // Fetch list of uploaded files
    public List<String> getUploadedFiles() {
        return contactListRepository.findAll()
                .stream()
                .map(ContactList::getFileName)
                .distinct()
                .collect(Collectors.toList());
    }

    // Edit a contact's number by ID
    public void editContactNumber(Long id, String newNumber) {
        Optional<ContactList> contactOptional = contactListRepository.findById(id);

        if (contactOptional.isPresent()) {
            ContactList contact = contactOptional.get();
            contact.setNumber(newNumber); // Update the number
            contactListRepository.save(contact); // Save the updated contact
        } else {
            throw new RuntimeException("Contact with id " + id + " not found.");
        }
    }

    // Delete a specific contact by ID
    public void deleteNumber(Long id) {
        Optional<ContactList> contactOptional = contactListRepository.findById(id);

        if (contactOptional.isPresent()) {
            contactListRepository.deleteById(id); // Delete the contact
        } else {
            throw new RuntimeException("Contact with id " + id + " not found.");
        }
    }

    // Delete all contacts associated with a file
    public void deleteFile(String fileName) {
        List<ContactList> contacts = contactListRepository.findByFileName(fileName);

        if (contacts.isEmpty()) {
            throw new RuntimeException("No contacts found for file: " + fileName);
        }

        contactListRepository.deleteAll(contacts); // Delete all contacts related to the file
    }
}