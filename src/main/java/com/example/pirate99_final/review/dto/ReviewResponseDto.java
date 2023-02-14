package com.example.pirate99_final.review.dto;

import com.example.pirate99_final.global.MsgResponseDto;
import com.example.pirate99_final.review.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto extends MsgResponseDto {
    private Long reviewId;
    private String username;
    private String content;

    public ReviewResponseDto(Review review){
        this.reviewId   =   review.getId();
        this.username   =   review.getUser().getUsername();
        this.content    =   review.getContent();
    }
}
