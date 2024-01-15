package com.example.CRUDContact.model;

import com.example.CRUDContact.dto.ContactInfoDTO;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "Contacts")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String contactNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Method to convert Contact entity to ContactInfoDTO
    public ContactInfoDTO toContactInfoDTO() {
        ContactInfoDTO dto = new ContactInfoDTO();
        dto.setId(id);
        dto.setFullName(firstName + " " + lastName);
        dto.setAddress(address);
        dto.setContactNumber(contactNumber);
        dto.setCreatedAt(createdAt);
        dto.setUpdatedAt(updatedAt);
        return dto;
    }
}
