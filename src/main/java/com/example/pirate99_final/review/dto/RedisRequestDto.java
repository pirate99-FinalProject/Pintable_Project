package com.example.pirate99_final.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RedisRequestDto {
    private Long storeid;

    public RedisRequestDto(Long storeId){
        this.storeid = storeId;
    }
}
