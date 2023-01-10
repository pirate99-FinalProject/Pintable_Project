package com.example.pirate99_final.review.repository;

import com.example.pirate99_final.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByOrderByIdAtDesc();
}
