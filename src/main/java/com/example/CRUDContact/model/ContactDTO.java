package com.example.CRUDContact.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContactDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String contactNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String fullName; // Include the full name field

    // No business logic in DTOs, only storage and accessors
}
