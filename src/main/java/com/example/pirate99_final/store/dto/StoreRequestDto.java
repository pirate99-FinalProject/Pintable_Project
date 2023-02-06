package com.example.pirate99_final.store.dto;

import lombok.Getter;

@Getter
public class StoreRequestDto{
    private String address;                                         // 주소
    private String roadNameAddress;                                 // 도로명주소
    private int postNumber;                                         // 우편주소
    private String storeName;                                       // 상호명
    private String typeOfBusiness;                                  // 업종명
    private double xcoordinate;                                     // X좌표
    private double ycoordinate;                                     // Y좌표
    private double starScore;
    private int reviewCnt;
}
