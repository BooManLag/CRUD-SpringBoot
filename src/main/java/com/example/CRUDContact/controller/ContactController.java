package com.example.CRUDContact.controller;

import com.example.CRUDContact.model.Contact;
import com.example.CRUDContact.service.ContactValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.CRUDContact.repository.ContactRepo;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private ContactValidationService contactValidationService; // Validation Service

    @GetMapping("/contacts")
    public ResponseEntity<List<String>> getAllContactFullNames() {
        List<Contact> contactList = contactRepo.findAll();
        if (contactList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Create a list of full names using the getFullName method
        List<String> fullNames = contactList.stream()
                .map(Contact::getFullName)
                .toList();

        return ResponseEntity.ok(fullNames);
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        return contactRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/addcontact")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        try {
            contactValidationService.validate(contact); // Validate before saving
            Contact newContact = contactRepo.save(contact);
            return ResponseEntity.status(201).body(newContact);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/contacts/{id}")
    public ResponseEntity<Contact> patchContact(@PathVariable Long id, @RequestBody Contact contact) {
        return contactRepo.findById(id)
                .map(existingContact -> {
                    if (contact.getFirstName() != null) existingContact.setFirstName(contact.getFirstName());
                    if (contact.getLastName() != null) existingContact.setLastName(contact.getLastName());
                    if (contact.getAddress() != null) existingContact.setAddress(contact.getAddress());
                    if (contact.getContactNumber() != null) existingContact.setContactNumber(contact.getContactNumber());
                    Contact updatedContact = contactRepo.save(existingContact);
                    return ResponseEntity.ok(updatedContact);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Object> deleteContact(@PathVariable Long id) {
        if (contactRepo.existsById(id)) {
            contactRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

