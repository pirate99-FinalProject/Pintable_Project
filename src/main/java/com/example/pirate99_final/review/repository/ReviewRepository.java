package com.example.pirate99_final.review.repository;

import com.example.pirate99_final.review.entity.Review;
import com.example.pirate99_final.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findTop10ByStoreOrderByIdDesc(Store store);
    Optional<Review>  findByStoreAndId(Store store, Long id);
}
