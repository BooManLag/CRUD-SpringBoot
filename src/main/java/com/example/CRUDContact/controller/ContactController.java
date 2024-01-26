package com.example.CRUDContact.controller;

import com.example.CRUDContact.model.Contact;
import com.example.CRUDContact.service.ContactValidationService;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<List<Contact>> getAllContacts(
            @RequestParam(name = "sortField", defaultValue = "id") String sortField,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection
    ) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortField);

        List<Contact> contactList = contactRepo.findAll(sort);
        if (contactList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(contactList);
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        return contactRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addContact(@RequestBody Contact contact) {
        try {
            // Use regex patterns for validation
            contactValidationService.validate(contact);

            Contact newContact = contactRepo.save(contact);
            return ResponseEntity.status(201).body(newContact);
        } catch (ValidationException e) { // Handle ValidationException
            return ResponseEntity.badRequest().body(e.getMessage());
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