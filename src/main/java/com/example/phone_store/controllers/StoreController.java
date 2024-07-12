package com.example.phone_store.controllers;

import com.example.phone_store.models.Store;
import com.example.phone_store.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    // Get all stores
    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        return new ResponseEntity<>(stores, HttpStatus.OK);
    }

    // Get store by ID
    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found with id: " + id));
        return new ResponseEntity<>(store, HttpStatus.OK);
    }

    // Create a new store
    @PostMapping
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        Store createdStore = storeRepository.save(store);
        return new ResponseEntity<>(createdStore, HttpStatus.CREATED);
    }

    // Update a store
    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody Store storeDetails) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found with id: " + id));

        store.setName(storeDetails.getName());
        store.setAddress(storeDetails.getAddress());

        Store updatedStore = storeRepository.save(store);
        return new ResponseEntity<>(updatedStore, HttpStatus.OK);
    }

    // Delete a store
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found with id: " + id));

        storeRepository.delete(store);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
