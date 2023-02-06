package com.example.pirate99_final.review.dto;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private String username;
    private String content;
    private double starScore;
    private Long storeid;
    private Long userid;
}
