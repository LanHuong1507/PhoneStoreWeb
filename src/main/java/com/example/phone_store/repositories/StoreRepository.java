package com.example.phone_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.phone_store.models.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{
    
}
