package com.example.pirate99_final.store.config;

import lombok.Data;

@Data
public class SearchCondition {

    private Long    storeId;            // DB에 저장되는 id
    private String  address;            // 일반 주소
    private String  roadNameAddress;    // 도로명 주소
    private int     postNumber;         // 우편 번호
    private String  storeName;          // 가게 이름
    private String  typeOfBusiness;     // 업종
    private double  xCoordinate;        // 위도(x좌표)
    private double  yCoordinate;        // 경도(y좌표)
    private double  starScore;          // 별점
    private int     reviewCnt;          // 리뷰 갯수

}
