package com.example.phone_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.phone_store.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
    
}
