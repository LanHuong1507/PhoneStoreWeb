package com.example.phone_store.controllers;

import com.example.phone_store.models.Phone;
import com.example.phone_store.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phones")
public class PhoneController {

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneController(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    // Get all phones
    @GetMapping
    public List<Phone> getAllPhones() {
        List<Phone> phones = phoneRepository.findAll(); // Ensure reviews are fetched appropriately
        return phones;
    }
    // Get phone by ID
    @GetMapping("/{id}")
    public ResponseEntity<Phone> getPhoneById(@PathVariable Long id) {
        Phone phone = phoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phone not found with id: " + id));
        return new ResponseEntity<>(phone, HttpStatus.OK);
    }

    // Create a new phone
    @PostMapping
    public ResponseEntity<Phone> createPhone(@RequestBody Phone phone) {
        Phone createdPhone = phoneRepository.save(phone);
        return new ResponseEntity<>(createdPhone, HttpStatus.CREATED);
    }

    // Update a phone
    @PutMapping("/{id}")
    public ResponseEntity<Phone> updatePhone(@PathVariable Long id, @RequestBody Phone phoneDetails) {
        Phone phone = phoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phone not found with id: " + id));

        phone.setName(phoneDetails.getName());
        phone.setPrice(phoneDetails.getPrice());
        phone.setDescription(phoneDetails.getDescription());

        Phone updatedPhone = phoneRepository.save(phone);
        return new ResponseEntity<>(updatedPhone, HttpStatus.OK);
    }

    // Delete a phone
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id) {
        Phone phone = phoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phone not found with id: " + id));

        phoneRepository.delete(phone);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
