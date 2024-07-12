package com.example.phone_store.controllers;

import com.example.phone_store.models.Review;
import com.example.phone_store.repositories.PhoneRepository;
import com.example.phone_store.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final PhoneRepository phoneRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository, PhoneRepository phoneRepository) {
        this.reviewRepository = reviewRepository;
        this.phoneRepository = phoneRepository;
    }

    // Get all reviews
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // Get review by ID
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    // Create a new review for a phone
    @PostMapping("/phone/{phoneId}")
    public ResponseEntity<Review> createReview(@PathVariable Long phoneId, @RequestBody Review review) {
        return phoneRepository.findById(phoneId).map(phone -> {
            review.setPhone(phone);
            Review createdReview = reviewRepository.save(review);
            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        }).orElseThrow(() -> new RuntimeException("Phone not found with id: " + phoneId));
    }

    // Update a review
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        review.setAuthor(reviewDetails.getAuthor());
        review.setContent(reviewDetails.getContent());
        review.setRating(reviewDetails.getRating());

        Review updatedReview = reviewRepository.save(review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    // Delete a review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        reviewRepository.delete(review);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
