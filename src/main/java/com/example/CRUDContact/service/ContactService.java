package com.example.CRUDContact.service;

import com.example.CRUDContact.model.Contact;
import com.example.CRUDContact.model.ContactDTO;
import com.example.CRUDContact.repository.ContactRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepo contactRepository;

    @Autowired
    public ContactService(ContactRepo contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ContactDTO> findAll(Sort sort) {
        return contactRepository.findAll(sort).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ContactDTO findById(Long id) {
        return contactRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public ContactDTO save(ContactDTO dto) {
        Contact contact = convertToEntity(dto);
        return convertToDTO(contactRepository.save(contact));
    }

    public void deleteById(Long id) {
        contactRepository.deleteById(id);
    }

    // Helper method to convert Entity to DTO
    public ContactDTO convertToDTO(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setAddress(contact.getAddress());
        dto.setContactNumber(contact.getContactNumber());
        dto.setCreatedAt(contact.getCreatedAt());
        dto.setUpdatedAt(contact.getUpdatedAt());
        dto.setFullName(contact.getFirstName() + " " + contact.getLastName());
        return dto;
    }

    // Helper method to convert DTO to Entity
    public Contact convertToEntity(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setId(dto.getId());
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        contact.setAddress(dto.getAddress());
        contact.setContactNumber(dto.getContactNumber());
        return contact;
    }

    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        // Check if the contact with the given id exists
        Contact existingContact = contactRepository.findById(id).orElse(null);
        if (existingContact == null) {
            // Return null or handle it differently if the contact doesn't exist
            return null;
        }

        // Update the existing contact with values from the DTO
        updateContactDetails(existingContact, contactDTO);
        Contact updatedContact = contactRepository.save(existingContact);
        return convertToDTO(updatedContact);
    }

    private void updateContactDetails(Contact existingContact, ContactDTO contactDTO) {
        if (contactDTO.getFirstName() != null) {
            existingContact.setFirstName(contactDTO.getFirstName());
        }
        if (contactDTO.getLastName() != null) {
            existingContact.setLastName(contactDTO.getLastName());
        }
        if (contactDTO.getAddress() != null) {
            existingContact.setAddress(contactDTO.getAddress());
        }
        if (contactDTO.getContactNumber() != null) {
            existingContact.setContactNumber(contactDTO.getContactNumber());
        }

    }

}