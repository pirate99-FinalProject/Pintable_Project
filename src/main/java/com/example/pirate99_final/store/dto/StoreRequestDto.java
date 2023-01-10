package com.example.pirate99_final.store.dto;

import com.example.pirate99_final.store.entity.Store;
import lombok.Getter;

@Getter
public class StoreRequestDto{

    private String address;                                         // 주소
    private String roadNameAddress;                                 // 도로명주소
    private int postNumber;                                         // 우편주소
    private String storeName;                                       // 상호명
    private String typeOfBusiness;                                  // 업종명
    private double xCoordinate;                                     // X좌표
    private double yCoordinate;                                     // Y좌표
    private double starScore;                                       // 별점
    private int reviewCnt;                                          // 리뷰수
}
