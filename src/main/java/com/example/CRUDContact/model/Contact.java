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

    // Exclude first name and last name from getters
    @Getter
    private String address;

    @Getter
    private String contactNumber;

    @Getter
    private LocalDateTime createdAt;

    @Getter
    private LocalDateTime updatedAt;

    // Calculate the full name
    private String getFullName() {
        return firstName + " " + lastName;
    }

}
