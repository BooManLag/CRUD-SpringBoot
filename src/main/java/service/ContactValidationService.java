package service;

import jakarta.xml.bind.ValidationException;
import model.Contact;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class ContactValidationService {

    private static final Pattern NAME_PATTERN = Pattern.compile(".{2,}");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile(".{5,}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\d{11}");

    public void validate(Contact contact) throws ValidationException {

        if(contact.getFirstName() == null || contact.getFirstName().trim().isEmpty()) {
            throw new ValidationException("First name is required");
        }

        if(contact.getLastName() == null || contact.getLastName().trim().isEmpty()) {
            throw new ValidationException("Last name is required");
        }

        if(!NAME_PATTERN.matcher(contact.getFirstName()).matches()) {
            throw new ValidationException("Invalid first name");
        }

        if(!NAME_PATTERN.matcher(contact.getLastName()).matches()) {
            throw new ValidationException("Invalid last name");
        }

        if(contact.getAddress() == null || contact.getAddress().trim().isEmpty()) {
            throw new ValidationException("Address is required");
        }

        if(!ADDRESS_PATTERN.matcher(contact.getAddress()).matches()) {
            throw new ValidationException("Invalid address");
        }

        if(contact.getContactNumber() == null || contact.getContactNumber().trim().isEmpty()) {
            throw new ValidationException("Phone number is required");
        }

        if(!PHONE_PATTERN.matcher(contact.getContactNumber()).matches()) {
            throw new ValidationException("Invalid phone number");
        }

        if(contact.getCreatedAt() == null) {
            contact.setCreatedAt(LocalDateTime.now());
        }

        contact.setUpdatedAt(LocalDateTime.now());
    }

}

