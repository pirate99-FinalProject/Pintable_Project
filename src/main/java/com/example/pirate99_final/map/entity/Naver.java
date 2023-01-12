package com.example.pirate99_final.map.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Naver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                // DB에 저장되는 id
    private String address;         // 일반 주소
    private String roadnameaddress; // 도로명 주소
    private int postnumber;         // 우편 번호
    private String storename;       // 가게 이름
    private String typeofbusiness;  // 업종
    private double xcoordinate;     // 위도(x좌표)
    private double ycoordinate;     // 경도(y좌표)
    private double starscore;       // 별점
    private int reviewcnt;          // 리뷰 갯수
}
