package com.example.pirate99_final.store.dto;

import lombok.Getter;

@Getter
public class QuerydslDto {
    private String address;                                         // 주소
    private String roadNameAddress;                                 // 도로명주소
    private int postNumber;                                         // 우편주소
    private String storeName;                                       // 상호명
    private String typeOfBusiness;                                  // 업종명
    private double xcoordinate;                                     // X좌표
    private double ycoordinate;                                     // Y좌표

    private int waitingCnt;

    private String limitWaitingCnt;

    public QuerydslDto(String address, String roadNameAddress, int postNumber, String storeName, String typeOfBusiness, double xcoordinate, double ycoordinate, int waitingCnt, int limitWaitingCnt){
        this.address = address;
        this.roadNameAddress = roadNameAddress;
        this.postNumber = postNumber;
        this.storeName = storeName;
        this.typeOfBusiness = typeOfBusiness;
        this.xcoordinate = xcoordinate;
        this.ycoordinate = ycoordinate;
        this.waitingCnt = waitingCnt;

        if(limitWaitingCnt == 0){
            this.limitWaitingCnt = "-";
        } else
            this.limitWaitingCnt = Integer.toString(limitWaitingCnt);
    }
}