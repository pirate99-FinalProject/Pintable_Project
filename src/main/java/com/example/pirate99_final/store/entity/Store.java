package com.example.pirate99_final.store.entity;

import com.example.pirate99_final.global.entity.TimeStamped;
import com.example.pirate99_final.store.dto.StoreRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Store extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false)
    private String address;                                             // 주소

    @Column(nullable = false)
    private String roadNameAddress;                                     // 도로명주소

    @Column(nullable = false)
    private int postNumber;                                             // 우편번호

    @Column(nullable = false)
    private String storeName;                                           // 상호명

    @Column(nullable = false)
    private String typeOfBusiness;                                      // 업종명

    @Column(nullable = false)
    private double xCoordinate;                                         // X좌표

    @Column(nullable = false)
    private double yCoordinate;                                         // Y좌표

    @Column(nullable = false)
    private double starScore;                                           // 별점

    @Column(nullable = false)
    private int reviewCnt;                                              // 리뷰수

    //
    public Store(StoreRequestDto requestDto){
        this.address            =   requestDto.getAddress();            // 주소
        this.roadNameAddress    =   requestDto.getRoadNameAddress();    // 도로명주소
        this.postNumber         =   requestDto.getPostNumber();         // 우편번호
        this.storeName          =   requestDto.getStoreName();          // 상호명
        this.typeOfBusiness     =   requestDto.getTypeOfBusiness();     // 업종명
        this.xCoordinate        =   requestDto.getXcoordinate();        // X좌표
        this.yCoordinate        =   requestDto.getYcoordinate();        // Y좌표
        this.starScore          =   0;                                  // 별점
        this.reviewCnt          =   0;                                  // 리뷰수

    }
}
