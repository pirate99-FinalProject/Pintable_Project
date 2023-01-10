package com.example.pirate99_final.review.controller;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.review.dto.ReviewRequestDto;
import com.example.pirate99_final.review.dto.ReviewResponseDto;
import com.example.pirate99_final.review.service.ReviewService;
import com.example.pirate99_final.store.dto.StoreRequestDto;
import com.example.pirate99_final.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    // connect to StoreService
    private final ReviewService reviewService;

    // DB save
    @PostMapping("/review/{stordId}")
    public MsgResponseDto createReview(@PathVariable long stordId, @RequestBody ReviewRequestDto requestDto){
        return reviewService.createReview(stordId, requestDto);
    }

    // DB select all
    @GetMapping("/review/{stordId}")
    public List<ReviewResponseDto> getReviews(@PathVariable Long storeId){return reviewService.getReviews(storeId);}

    // DB select one
    @GetMapping("/review/{stordId}/{reviewId}")
    public ReviewResponseDto getReview(@PathVariable long stordId, @PathVariable long reviewId){return reviewService.getReview(stordId, reviewId);}


    // DB delete
    @DeleteMapping("/review/{storeId}/{reviewId}")
    public MsgResponseDto deleteReview(@PathVariable long storeId, @PathVariable Long reviewId) {
        return reviewService.deleteReview(storeId, reviewId);
    }
}
