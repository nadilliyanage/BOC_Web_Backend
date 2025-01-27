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

    public List<ContactListDTO> findByFileName(String fileName) {
        List<ContactList> contactList = contactListRepository.findByFileName(fileName);
        return contactList.stream()
                .map(contact -> new ContactListDTO(contact.getNumber()))
                .collect(Collectors.toList());
    }

    public void uploadCSV(MultipartFile file) throws Exception {
        // Parsing CSV file
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            ContactList contact = new ContactList();
            contact.setNumber(line.trim());
            contact.setFileName(file.getOriginalFilename());
            contactListRepository.save(contact);
        }
    }

    public List<String> getUploadedFiles() {
        return contactListRepository.findAll()
                .stream()
                .map(ContactList::getFileName)
                .distinct()
                .collect(Collectors.toList());
    }

    // Method to edit (update) a contact's number by user id
    public void editContactNumber(Long id, String newNumber) {
        Optional<ContactList> contactOptional = contactListRepository.findById(id);

        if (contactOptional.isPresent()) {
            ContactList contact = contactOptional.get();
            contact.setNumber(newNumber);  // Set the new number
            contactListRepository.save(contact);  // Save the updated contact
        } else {
            throw new RuntimeException("Contact with id " + id + " not found.");
        }
    }

    // Method to delete contacts based on the file name
    public void deleteFile(String fileName) {
        List<ContactList> contacts = contactListRepository.findByFileName(fileName);

        if (contacts.isEmpty()) {
            throw new RuntimeException("No contacts found for file: " + fileName);
        }

        // Delete all contacts related to the file
        contactListRepository.deleteAll(contacts);
    }
}
