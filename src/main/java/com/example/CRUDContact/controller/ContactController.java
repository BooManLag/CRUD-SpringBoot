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
@CrossOrigin
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactValidationService contactValidationService; // Validation Service

    @GetMapping("/contacts")
    public ResponseEntity<List<ContactDTO>> getAllContacts(
            @RequestParam(name = "sortField", defaultValue = "id") String sortField,
            @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection
    ) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortField);

        List<ContactDTO> contactList = contactService.findAll(sort);
        if (contactList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(contactList);
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        ContactDTO contact = contactService.findById(id);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contact);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addContact(@RequestBody ContactDTO contactDTO) {
        try {
            // Use regex patterns for validation
            contactValidationService.validate(contactDTO);

            ContactDTO newContact = contactService.save(contactDTO);
            return ResponseEntity.status(201).body(newContact);
        } catch (ValidationException e) { // Handle ValidationException
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/contacts/{id}")
    public ResponseEntity<ContactDTO> patchContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        ContactDTO updatedContact = contactService.updateContact(id, contactDTO);
        if (updatedContact == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Object> deleteContact(@PathVariable Long id) {
        if (contactService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        contactService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}