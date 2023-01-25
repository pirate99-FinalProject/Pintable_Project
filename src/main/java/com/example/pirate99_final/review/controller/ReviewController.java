package com.example.pirate99_final.review.controller;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.review.dto.ReviewRequestDto;
import com.example.pirate99_final.review.dto.ReviewResponseDto;
import com.example.pirate99_final.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
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
    public MsgResponseDto createReview(@PathVariable Long stordId, @RequestBody ReviewRequestDto requestDto){
        return reviewService.createReview(stordId, requestDto);
    }

    // DB select all
    @GetMapping("/review/{storeId}")
    public List<ReviewResponseDto> getReviews(@PathVariable Long storeId){return reviewService.getReviews(storeId);}

    // DB select one

    @GetMapping("/review/{stordId}/{reviewId}")
    public ReviewResponseDto getReview(@PathVariable Long stordId, @PathVariable Long reviewId){return reviewService.getReview(stordId, reviewId);}


    // DB delete
    @DeleteMapping("/review/{storeId}/{reviewId}")
    public MsgResponseDto deleteReview(@PathVariable Long storeId, @PathVariable Long reviewId) {
        return reviewService.deleteReview(storeId, reviewId);
    }
}
