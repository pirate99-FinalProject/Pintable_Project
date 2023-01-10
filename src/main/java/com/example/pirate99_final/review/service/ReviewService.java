package com.example.pirate99_final.review.service;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.global.exception.CustomException;
import com.example.pirate99_final.global.exception.ErrorCode;
import com.example.pirate99_final.review.dto.ReviewRequestDto;
import com.example.pirate99_final.review.dto.ReviewResponseDto;
import com.example.pirate99_final.review.entity.Review;
import com.example.pirate99_final.review.repository.ReviewRepository;
import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.store.repository.StoreRepository;
import com.example.pirate99_final.user.entity.User;
import com.example.pirate99_final.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.pirate99_final.global.exception.SuccessCode.CREATE_REVIEW;
import static com.example.pirate99_final.global.exception.SuccessCode.DELETE_REVIEW;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    // Store Create function
    public MsgResponseDto createReview(long id, ReviewRequestDto requestDto){
        //1. create store Object and insert DB

        Store store = storeRepository.findById(id).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_USER_ERROR)
        );

        Review review = new Review(requestDto, store, user);

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

    // DB update function
    @Transactional
    public ReviewResponseDto update(Long storeId, Long reviewid, ReviewRequestDto requestDto) {

        Store store = storeRepository.findById(storeId).orElseThrow(()->
                new CustomException(ErrorCode.NOT_FOUND_STORE_ERROR)
        );

        Review review = reviewRepository.findByStoreAndId(store, reviewid).orElseThrow(                                             // find memo
                () -> new CustomException(ErrorCode.NOT_FOUND_REVIEW_ERROR)
        );

        review.update(requestDto);
        return new ReviewResponseDto(review);
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
