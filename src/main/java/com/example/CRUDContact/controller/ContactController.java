package com.example.CRUDContact.controller;

import com.example.CRUDContact.model.ContactDTO;
import com.example.CRUDContact.service.ContactService;
import com.example.CRUDContact.service.ContactValidationService;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    private final ContactValidationService contactValidationService;

    @Autowired
    public ContactController(ContactService contactService, ContactValidationService contactValidationService) {
        this.contactService = contactService;
        this.contactValidationService = contactValidationService;
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts(
            @RequestParam(name = "sortField", defaultValue = "id") String sortField,
            @RequestParam(name = "sortDirection", defaultValue = "asc") String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortField);
        List<ContactDTO> contactDTOs = contactService.findAll(sort);
        return contactDTOs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(contactDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        ContactDTO contactDTO = contactService.findById(id);
        return contactDTO != null ? ResponseEntity.ok(contactDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/addcontact")
    public ResponseEntity<?> addContact(@RequestBody ContactDTO contactDTO) {
        try {
            contactValidationService.validateDTO(contactDTO); // This method should be created in your validation service
            ContactDTO newContactDTO = contactService.save(contactDTO);
            return ResponseEntity.status(201).body(newContactDTO);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        ContactDTO updatedContactDTO = contactService.updateContact(id, contactDTO);
        if (updatedContactDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedContactDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        contactService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Other controller methods...
}