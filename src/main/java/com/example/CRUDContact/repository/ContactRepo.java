package com.example.CRUDContact.repository;

import com.example.CRUDContact.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactRepo extends JpaRepository <Contact,Long> {

}
