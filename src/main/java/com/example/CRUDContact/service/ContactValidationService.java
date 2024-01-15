package com.example.CRUDContact.service;

import com.example.CRUDContact.model.Contact;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
public class ContactValidationService {

    private static final Pattern NAME_PATTERN = Pattern.compile(".{2,}");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile(".{5,}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{11}");

    public void validate(Contact contact) throws ValidationException {

        if (contact.getFirstName() == null || contact.getFirstName().trim().isEmpty()) {
            throw new ValidationException("First name is required.");
        }

        if (contact.getLastName() == null || contact.getLastName().trim().isEmpty()) {
            throw new ValidationException("Last name is required.");
        }

        if (!NAME_PATTERN.matcher(contact.getFirstName()).matches()) {
            throw new ValidationException("First name should be at least 2 characters long.");
        }

        if (!NAME_PATTERN.matcher(contact.getLastName()).matches()) {
            throw new ValidationException("Last name should be at least 2 characters long.");
        }

        if (contact.getAddress() == null || contact.getAddress().trim().isEmpty()) {
            throw new ValidationException("Address is required.");
        }

        if (!ADDRESS_PATTERN.matcher(contact.getAddress()).matches()) {
            throw new ValidationException("Address should be at least 5 characters long.");
        }

        if (contact.getContactNumber() == null || contact.getContactNumber().trim().isEmpty()) {
            throw new ValidationException("Phone number is required.");
        }

        if (!PHONE_PATTERN.matcher(contact.getContactNumber()).matches()) {
            throw new ValidationException("Phone number should be 11 digits long.");
        }

        if (contact.getCreatedAt() == null) {
            contact.setCreatedAt(LocalDateTime.now());
        }

        contact.setUpdatedAt(LocalDateTime.now());
    }
}
