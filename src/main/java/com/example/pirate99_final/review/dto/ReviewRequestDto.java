package com.example.pirate99_final.review.dto;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private String userId;
    private String content;
    private double starScore;
}