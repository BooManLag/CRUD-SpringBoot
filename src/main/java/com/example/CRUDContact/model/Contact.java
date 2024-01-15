package com.example.CRUDContact.model;

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

    // Calculate the full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Exclude first name and last name from getters
    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
