package com.example.CRUDContact.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactInfoDTO {
    private Long id;
    private String fullName;
    private String address;
    private String contactNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}