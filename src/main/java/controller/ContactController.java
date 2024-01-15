package controller;

import model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import repository.ContactRepo;

import java.util.List;
import java.util.Optional;

@RestController
public class ContactController {

    @Autowired
    private ContactRepo contactRepo;

    // Get all contacts
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts(){
        try {
            List<Contact> contactList = contactRepo.findAll();

            if(contactList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contactList, HttpStatus.OK);

        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get single contact
    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id){
        try{
            Contact contact = contactRepo.findById(id).get();
            return new ResponseEntity<>(contact, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add new contact
    @PostMapping("/addcontact")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact){
        try{
            Contact newContact = contactRepo.save(contact);
            return new ResponseEntity<>(newContact, HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update existing contact
    @PatchMapping("/contacts/{id}")
    public ResponseEntity<Contact> patchContact(@PathVariable Long id, @RequestBody Contact contact) {

        try {
            Contact existingContact = contactRepo.findById(id).get();

            Optional.ofNullable(contact.getFirstName())
                    .ifPresent(existingContact::setFirstName);

            Optional.ofNullable(contact.getLastName())
                    .ifPresent(existingContact::setLastName);

            Optional.ofNullable(contact.getAddress())
                    .ifPresent(existingContact::setAddress);

            Optional.ofNullable(contact.getContactNumber())
                    .ifPresent(existingContact::setContactNumber);

            Contact updatedContact = contactRepo.save(existingContact);
            return new ResponseEntity<>(updatedContact, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete contact
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable Long id){
        try{
            contactRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}