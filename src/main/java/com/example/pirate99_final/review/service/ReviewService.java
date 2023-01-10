package com.example.pirate99_final.review.service;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.CustomException;
import com.example.pirate99_final.global.exception.ErrorCode;
import com.example.pirate99_final.review.dto.ReviewRequestDto;
import com.example.pirate99_final.review.dto.ReviewResponseDto;
import com.example.pirate99_final.review.entity.Review;
import com.example.pirate99_final.review.repository.ReviewRepository;
import com.example.pirate99_final.store.dto.StoreRequestDto;
import com.example.pirate99_final.store.dto.StoreResponseDto;
import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.pirate99_final.global.exception.SuccessCode.CREATE_REVIEW;
import static com.example.pirate99_final.global.exception.SuccessCode.DELETE_REVIEW;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    // Store Create function
    public MsgResponseDto createReview(long id, ReviewRequestDto requestDto){
        //1. create store Object and insert DB
        Review review = new Review(requestDto);
        reviewRepository.save(review);
        return new MsgResponseDto(CREATE_REVIEW);
    }

    // Get memos from DB (all)
    public List<ReviewResponseDto> getReviews(long id) {
        // 1. Select All Memo
        List<Review> ListReview = reviewRepository.findAllByOrderByIdAtDesc();

        List<ReviewResponseDto> ListResponseDto = new ArrayList<>();

        for(Review review : ListReview){
            ListResponseDto.add(new ReviewResponseDto(review));
        }
        return ListResponseDto;
    }

    // Get store from DB (one)
    public ReviewResponseDto getReview(long storeId, long reviewId){
        Review review = reviewRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_REVIEW_ERROR)
        );

        ReviewResponseDto responseDto = new ReviewResponseDto(review);
        return responseDto;
    }

    // DB delete function (data delete)

    public MsgResponseDto deleteReview(Long storeId, Long reviewid) {

        Review review  = reviewRepository.findById(storeId).orElseThrow(                                             // find memo
                () -> new CustomException(ErrorCode.NOT_FOUND_ID_ERROR)
        );
        reviewRepository.deleteById(storeId);                                                          // 해당 게시물 삭제

        return  new MsgResponseDto(DELETE_REVIEW);
    }
}
