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

    // Method to calculate the full name
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
