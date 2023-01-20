package com.example.pirate99_final.review.dto;

import com.example.pirate99_final.store.entity.Store;
import com.example.pirate99_final.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewRequestDto {
    private String username;
    private String content;
    private double starScore;
    private Long storeid;
    private Long userid;

    public ReviewRequestDto(){
    }

    public static ReviewRequestDto createReviewMessageSaveDto(ReviewRequestDto reviewRequestDto, Store store, User user){
        return ReviewRequestDto.builder()
                .username(reviewRequestDto.getUsername())
                .content(reviewRequestDto.getContent())
                .starScore(reviewRequestDto.getStarScore())
                .storeid(store.getStoreId())
                .userid(user.getUserId())
                .build();
    }

}
