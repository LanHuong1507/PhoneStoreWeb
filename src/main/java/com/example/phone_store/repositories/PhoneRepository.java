package com.example.phone_store.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.phone_store.models.Phone;
import com.example.phone_store.models.Store;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long>{
    List<Phone> findByStore(Store store);
}
