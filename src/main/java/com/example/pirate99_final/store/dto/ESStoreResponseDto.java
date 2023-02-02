package com.example.pirate99_final.store.dto;

import com.example.pirate99_final.store.entity.StoreDocument;
import lombok.Getter;

@Getter
public class ESStoreResponseDto {
    private Long    storeId;            // 가게이름
    private String  address;            // 주소
    private String  roadNameAddress;    // 도로명주소
    private int     postNumber;         // 우편번호
    private String  storeName;          // 가게이름
    private String  typeOfBusiness;     // 업종
    private double  xcoordinate;        // 경도
    private double  ycoordinate;        // 위도
    private double  starScore;          // 별점
    private int     reviewCnt;          // 리뷰
    private int     waitingCnt;         // 웨이팅 숫자
    private String  limitWaitingCnt;    // 웨이팅 제한

    public ESStoreResponseDto(StoreDocument storeDocument) {
        this.storeId         = storeDocument.getStore_id();
        this.address         = storeDocument.getAddress();
        this.roadNameAddress = storeDocument.getRoadNameAddress();
        this.postNumber      = storeDocument.getPost_number();
        this.storeName       = storeDocument.getStoreName();
        this.typeOfBusiness  = storeDocument.getTypeOfBusiness();
        this.xcoordinate     = storeDocument.getX_coordinate();
        this.ycoordinate     = storeDocument.getY_coordinate();
        this.starScore       = storeDocument.getStarScore();
        this.reviewCnt       = storeDocument.getReviewCnt();
        this.waitingCnt      = 0;
        this.limitWaitingCnt = "test";
    }
}
